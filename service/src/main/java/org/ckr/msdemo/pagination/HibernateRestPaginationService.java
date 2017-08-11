package org.ckr.msdemo.pagination;
import org.hibernate.query.Query;
import com.google.gson.GsonBuilder;
import org.ckr.msdemo.pagination.PaginationContext.QueryRequest;
import org.ckr.msdemo.pagination.PaginationContext.QueryResponse;
import org.ckr.msdemo.pagination.PaginationContext.SortCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implement pagination query base on hibernate.<br>
 * Before it is used, please register this as a bean in Spring container and inject a valid session factory. Then,
 * call {@link HibernateRestPaginationService#query(String, Map, Function, Long)} to do query.
 */
@Service
public class HibernateRestPaginationService {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateRestPaginationService.class);

    @PersistenceContext
    EntityManager entityManager;


    /**
     * Do query base on a HQL. <br>
     * The HQL can include parameters. Please refer
     * {@link Query#setParameter(String, Object)} about
     * the format of the parameters in HQL.<br>
     * If a HQL include a section like <code>"/* param1|... *<span></span>/"</code>, that means this section will not
     * be exit in the HQL util parameter "param1" is specified.<br>
     * For example, assume the raw HQL is:<br>
     * "select t.field1, t.field2 from Table1 t where 1=1 /*param1| and t.field1 = :param1 *<span></span>/".<br>
     * If parameter "param1" is specified, the HQL will be:<br>
     * "select t.field1, t.field2 from Table1 t where 1=1 and t.field1 = :param1". <br>
     * Otherwise, the HQL will be:<br>
     * "select t.field1, t.field2 from Table1 t where 1=1 ".<br>
     * This feature is very useful for dynamic query scenario. <br>
     *
     * @param hql                 The HQL for the query
     * @param params              A map that include all parameters that will be used in the HQL. The key of this map object is
     *                            the parameter name. The value of this map object is the parameter value. This method call
     *                            org.hibernate.query.Query#setParameter(String, Object) for each pair in this map object.
     * @param mapper              If this is not null, it will be used to map object type that returned by hibernate
     *                            to object type that will be returned in the body of {@link QueryResponse}. For example, the HQL
     *                            will return an Object[]. However, the expected object type is DateView. Then, need to use
     *                            this mapper to map Object[] to DataView. Please refer usage of
     *                            {@link Query#()}.
     * @param maxNoRecordsPerPage The max no of records that will be returned by this method.
     * @return an {@link QueryResponse} object that include the query result and pagination info.
     * @see QueryResponse
     */
    public QueryResponse query(final String hql,
                               final Map<String, Object> params,
                               final Function<Object[], ?> mapper,
                               final Long maxNoRecordsPerPage) {

        return new BaseRestPaginationServiceTemplate() {


            @Override
            protected QueryResponse doQuery(QueryResponse response, QueryRequest request) {
                String queryStr = adjustQueryString(hql, params);

                response = doQueryContent(response, request, queryStr, params, mapper);
                response = doQueryTotalNoRecords(response, request, queryStr, params);
                return response;
            }

        }.query(maxNoRecordsPerPage);

    }

    /**
     * Do query base on a HQL. <br>
     * This is the same as {@link HibernateRestPaginationService#query(String, Map, Function, Long)} except
     * the maxNoRecordsPerPage parameter value is always 500.
     *
     * @see HibernateRestPaginationService#query(String, Map, Function, Long)
     */
    public QueryResponse query(final String hql,
                               final Map<String, Object> params,
                               Function<Object[], ?> mapper) {

        return query(hql, params, mapper, 500L);

    }


    @SuppressWarnings("unchecked")
    private QueryResponse doQueryContent(QueryResponse response,
                                         QueryRequest request,
                                         String queryStr,
                                         Map<String, Object> params,
                                         Function<Object[], ?> mapper) {

        String queryString = appendSortCriteria(queryStr, request);

        LOG.debug("get data HQL:{}", queryString);
        Query query = (Query) entityManager.createQuery(queryString);
        setQueryParameter(query, params);
        if (request != null && request.getStart() != null) {
            query.setFirstResult(request.getStart().intValue() - 1);
        }
        if (request != null && request.getEnd() != null) {
            query.setMaxResults((int) (request.getEnd() - request.getStart()) + 1);
        }
        List<Object> resultList;

        if (mapper != null) {
            Stream<Object[]> stream = (Stream<Object[]>) query.stream();
            resultList = stream.map(mapper).collect(Collectors.toList());

        } else {
            resultList = (List<Object>) query.getResultList();
        }
        LOG.debug("get data result:{}", new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(resultList));
        response.setContent(resultList);
        response.setStart(request.getStart());
        if ((long) resultList.size() > 0){
            response.setEnd(request.getStart() + (long) resultList.size() - 1);
        }else {
            response.setEnd((long) resultList.size());
        }
        return response;
    }


    private QueryResponse doQueryTotalNoRecords(QueryResponse response,
                                                QueryRequest request,
                                                String queryStr,
                                                Map<String, Object> params) {

        if (request == null || (request.getStart() == 0 && request.getEnd() == null)) {
            response.setTotal((long) response.getContent().size());
            return response;
        }


        String queryString = getHqlForTotalNoRecords(queryStr);

        LOG.debug("get total no of records HQL:{}", queryString);

        Query query = (Query) entityManager.createQuery(queryString);

        setQueryParameter(query, params);

        response.setTotal((Long) query.getSingleResult());
        LOG.info("getContent = {}" + response.getContent().size());
        PaginationContext.setResponseInfo(response.getStart(), response.getEnd() ,response.getTotal());
        LOG.debug("total number of records {}", response.getTotal());
        return response;
    }

    private String getHqlForTotalNoRecords(String queryStr) {

        String result;

        String upperQueryStr = queryStr.toUpperCase();


        int start = queryStr.indexOf("(");

        int end = queryStr.indexOf(")");

        int fromIndex;

        int queryStrLen = queryStr.length() - 1;

        do {
            if (queryStrLen < 0) {
                LOG.info("cannot find a top level 'FROM' from query string: '"
                                          + queryStr + "' . The i is < 0 already");
            }

            fromIndex = upperQueryStr.lastIndexOf("FROM", queryStrLen);
            if (fromIndex < 0) {
                LOG.info("cannot find a top level 'FROM' from query string: '"
                                          + queryStr + "' . Cannot find 'FROM'. The i = " + queryStrLen);
            }

            if (fromIndex <= end && fromIndex >= start) {
                queryStrLen = fromIndex - 1;
            } else {
                break;
            }

        }
        while (true);

        result = "SELECT COUNT(*) " + queryStr.substring(fromIndex);

        LOG.debug("HQL to get total number of records {}", result);

        return result;
    }

    private static void setQueryParameter(Query query, Map<String, Object> params) {

        if (params == null) {
            return;
        }

        params.entrySet().forEach(e -> query.setParameter(e.getKey(), e.getValue()));

    }

    private String appendSortCriteria(String queryString, QueryRequest request) {

        if (request == null) {
            return queryString;
        }

        List<SortCriteria> sortCriList = request.getSortCriteriaList();

        if (sortCriList == null || sortCriList.isEmpty()) {
            return queryString;
        }


        StringBuilder result = new StringBuilder(queryString);
        result.append(" order by ");

        for (int i = 0; i < sortCriList.size(); i++) {
            SortCriteria criteria = sortCriList.get(i);
            result.append(criteria.getFieldName());

            if (criteria.isAsc()) {
                result.append(" asc ");
            } else {
                result.append(" desc ");
            }

            if (i < sortCriList.size() - 1) {
                result.append(" , ");
            }

        }

        return result.toString();
    }


    /**
     * @param hql    hql to parse
     * @param params inject params to hql
     * @return standard hql query
     * <p>
     * sample query like like
     */
    // select u.userName, u.userDescription, u.locked from User u where 1=1
    // /*userName| and u.userName = :userName */
    // /*userDesc| and u.userDescription like :userDesc */
     /* will be parsed and set correspondent value according to @params,
     * if params contain userName, statement u.userName = :userName will append.
     * if not, statement userName| and u.userName = :userName will be deleted.
     */
    protected static String adjustQueryString(String hql, Map<String, Object> params) {

        StringBuffer queryStr = new StringBuffer(hql);

        LOG.debug("before adjustment, the query string is {}", queryStr);


        for (int startInd = queryStr.indexOf("/*"); startInd >= 0; startInd = queryStr.indexOf("/*")) {

            int endInd = queryStr.indexOf("*/", startInd);

            if (endInd < 0) {
                break;
            }

            String criteriaStr = queryStr.substring(startInd + 2, endInd);

            LOG.debug("criteria string: {}", criteriaStr);

            StringTokenizer tokenizer = new StringTokenizer(criteriaStr, "|");
            if (tokenizer.countTokens() < 2) {
                LOG.error("invalid criteria string: {}", criteriaStr);
            }

            String criteriaName = tokenizer.nextToken().trim();

            if (params.keySet().contains(criteriaName)) {

                String criteriaContent = tokenizer.nextToken();

                queryStr.replace(startInd, endInd + 2, criteriaContent);

            } else {

                queryStr.replace(startInd, endInd + 2, "");
            }
        }

        LOG.debug("after adjustment, the query string is {}", queryStr);
        return queryStr.toString();

    }


}

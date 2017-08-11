package org.ckr.msdemo.pagination;

import org.ckr.msdemo.pagination.PaginationContext.QueryRequest;
import org.ckr.msdemo.pagination.PaginationContext.QueryResponse;

/**
 * This is a util class that should be used by service class that implement pagination
 * query.
 * <p>
 * In controller layer, {@link PaginationContext} is used to parse pagination request info
 * from HTTP request and generate proper response. Inside {@link HibernateRestPaginationService#()}, developer
 * can call {@link PaginationContext#getQueryRequest()} to retrieve pagination request info from thread local.
 * This class is designed with GOF template method pattern and used to wrap up logic above.
 * Below is an example about how to use this class:</p>
 * <pre>
 * <code>
 *  return new BaseRestPaginationServiceTemplate() {
 *      &#064;Override
 *      protected QueryResponse&lt;Object&gt; doQuery(QueryResponse&lt;Object&gt; response, QueryRequest request) {
 *          // query data from somewhere
 *          ...
 *
 *          //set the query result into the response object.
 *          response.setContent(resultList);
 *          //set the total number of records of the query without pagination consideration
 *          response.setTotal(totalNoOfRecords);
 *
 *          return response;
 *      }
 *
 *  }.query(maxNoRecordsPerPage);
 * </code>
 * </pre>
 */
public abstract class BaseRestPaginationServiceTemplate {


    /**
     * Retrieve pagination request info(a {@link QueryRequest} object)
     * from {@link PaginationContext#getQueryRequest()} and create
     * a {@link QueryResponse} object. Use these 2 objects to call
     * {@link BaseRestPaginationServiceTemplate#doQuery(QueryResponse, QueryRequest)} so that developer can
     * just focus on how to implement the query logic inside a
     * {@link BaseRestPaginationServiceTemplate#doQuery(QueryResponse, QueryRequest)} implementation and no need to
     * think about how to retrieve or create those 2 objects.
     *
     * @param maxNoRecordsPerPage the max no of records will be returned by this query.
     * @return a {@link QueryResponse} that will be used by
     * {@link PaginationContext} to generate HTTP response.
     */
    public QueryResponse query(Long maxNoRecordsPerPage) {

        QueryRequest request = PaginationContext.getQueryRequest();

        if (request != null) {
            request = adjustRange(request, maxNoRecordsPerPage);
        }

        QueryResponse contentRange = new QueryResponse(request.getStart(), request.getEnd());

        contentRange = doQuery(contentRange, request);

        if (request != null && request.getStart() != null) {
            contentRange.setStart(request.getStart());
        }

        return contentRange;
    }

    /**
     * When this class is used, this abstract method must be implemented to provide real query logic.
     *
     * @param response After the query is done, the result and total no of records should be saved in this object.
     *                 This object must be returned.
     * @param request  Developer can retrieve pagination request info from this parameter. Such as the no of page.
     * @return a {@link QueryResponse} that will be used by
     * {@link PaginationContext} to generate HTTP response.
     */
    protected abstract QueryResponse doQuery(QueryResponse response, QueryRequest request);

    private QueryRequest adjustRange(QueryRequest request, Long maxNoRecordsPerPage) {

        if (request == null) {
            return null;
        }

        if (request.getStart() == null) {
            request.setStart((long) 0);
        }

        if (maxNoRecordsPerPage != null) {
            if (request.getEnd() == null || request.getEnd() - request.getStart() > maxNoRecordsPerPage - 1) {
                //make sure the total number records will not exceed the maxNoRecordPerPage
                request.setEnd(request.getStart() + maxNoRecordsPerPage - 1);
            }
        }

        return request;
    }


}

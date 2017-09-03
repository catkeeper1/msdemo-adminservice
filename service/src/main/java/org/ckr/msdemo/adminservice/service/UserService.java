package org.ckr.msdemo.adminservice.service;

import org.ckr.msdemo.adminservice.annotation.ReadOnlyTransaction;
import org.ckr.msdemo.adminservice.annotation.ReadWriteTransaction;
import org.ckr.msdemo.adminservice.entity.Role;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.repository.UserRepository;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserQueryView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.adminservice.vo.UserWithRole;
import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.ckr.msdemo.pagination.PaginationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * User related business logic.
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    JpaRestPaginationService jpaRestPaginationService;

    /**
     * Query user detail info by user ID.
     * This method should return the basic user info and the corresponding role info.
     * Please refer {@link UserDetailView} for what kind of user info will be
     * returned.
     *
     * @param userName The user ID.
     * @return detail info of a user.
     * {@link ApplicationException} will be thrown if user is not exist.
     */
    @ReadOnlyTransaction
    public UserDetailView getUser(String userName) {

        UserDetailView result = new UserDetailView();

        User user = this.userRepository.findByUserName(userName);

        if (user == null) {
            throw new ApplicationException("User '" + userName + "' is not exist.")
                .addMessage("security.maintain_user.not_existing_user",
                    new Object[] {userName});

        }

        result.setUserName(user.getUserName());
        result.setUserDescription(user.getUserDescription());
        result.setLocked(user.getLocked());

        List<UserServiceForm.RoleServiceForm> roleList = new ArrayList<>();

        for (Role role : user.getRoles()) {

            UserDetailView.RoleDetailView roleView = new UserDetailView.RoleDetailView();

            roleView.setRoleCode(role.getRoleCode());
            roleView.setRoleDescription(role.getRoleDescription());

            roleList.add(roleView);
        }

        result.setRoles(roleList);

        return result;
    }

    /**
     * create an user specify by {@link UserServiceForm}.
     *
     * @param userForm userForm from web page
     *                 {@link ApplicationException} will be thrown when
     *                 <ol><li>user name id dupulicate</li>
     *                 <li>user name is empty</li>
     *                 <li>user description is empty</li></ol>
     */
    @ReadWriteTransaction
    public void createUser(UserServiceForm userForm) {

        LOG.debug("create new user.");

        User user = new User();
        validateUserInfo(userForm);

        if (this.userRepository.findByUserName(userForm.getUserName()) != null) {
            throw new ApplicationException("security.maintain_user.duplicated_user");
        }

        user.setUserName(userForm.getUserName());
        user.setUserDescription(userForm.getUserDescription());
        user.setLocked(Boolean.FALSE);

        user.setPassword(encodePassword(userForm.getPassword()));

        this.userRepository.save(user);
    }

    /**
     * Validate user information according to UserServiceForm.
     * <li>user name is not empty
     * <li>user description is not empty
     *
     * @param user UserServiceForm
     */

    private void validateUserInfo(UserServiceForm user) {
        if (user.getUserName() == null || "".equals(user.getUserName())) {
            throw new ApplicationException("security.maintain_user.user_name_empty");
        }

        if (user.getUserDescription() == null || "".equals(user.getUserDescription())) {
            throw new ApplicationException("security.maintain_user.user_desc_empty");
        }
    }

    private String encodePassword(String pwd) {
        return pwd;
    }

    /**
     * find all users and return a page.
     *
     * @return List of User
     */
    @ReadOnlyTransaction
    public List<User> queryUsers() {
        //Authentication a = SecurityContextHolder.getContext().getAuthentication();

        //LOG.debug("user ID is {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findAll();
    }

    /**
     * Find users with user name and user description.
     *
     * @param userName user name
     * @param userDesc user description
     * @return List of UserQueryView
     */
    @ReadOnlyTransaction
    public List<UserQueryView> queryUsers2(String userName, String userDesc) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        if (!StringUtils.isEmpty(userDesc)) {
            params.put("userDesc", "%" + userDesc + "%");
        }

        String queryStr = "select u.userName, u.userDescription, u.locked from User u where 1=1 "
            + "/*userName| and u.userName = :userName */"
            + "/*userDesc| and u.userDescription like :userDesc */";

        Function<Object[], UserQueryView> mapper = new Function<Object[], UserQueryView>() {

            @Override
            public UserQueryView apply(Object[] row) {

                UserQueryView view = new UserQueryView();

                view.setUserName((String) row[0]);
                view.setUserDescription((String) row[1]);
                view.setLocked(((Boolean) row[2]).toString());

                return view;
            }


        };
        List<UserQueryView> result = jpaRestPaginationService.query(queryStr, params, mapper);

        LOG.debug("pagination query result {}", result);

        return result;
    }

    /**
     * Find users with user name and user description.
     *
     * @param userName user name
     * @param userDesc user description
     * @return List of UserWithRole
     */
    @ReadOnlyTransaction
    public List<UserWithRole> queryUsersWithRoles(String userName, String userDesc) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }
        if (!StringUtils.isEmpty(userDesc)) {
            params.put("userDesc", "%" + userDesc + "%");
        }

        String queryStr = "select u.userName as userName, u.userDescription as userDescription, u.locked as locked, "
            + " u.password as password, g.roleCode as roleCode, g.roleDescription as roleDescription"
            + " from User u left join u.roles as g where 1=1 "
            + "/*userName| and u.userName = :userName */"
            + "/*userDesc| and u.userDescription like :userDesc */";

        Function<Object[], UserWithRole> mapper = new Function<Object[], UserWithRole>() {

            @Override
            public UserWithRole apply(Object[] row) {

                UserWithRole view = new UserWithRole();

                view.setUserName((String) row[0]);
                view.setUserDescription((String) row[1]);
                view.setLocked(((Boolean) row[2]));
                view.setPassword((String) row[3]);
                view.setRoleCode((String) row[4]);
                view.setRoleDescription((String) row[5]);

                return view;
            }


        };
        List<UserWithRole> result = jpaRestPaginationService.query(queryStr, params, mapper);

        LOG.debug("pagination query result {}", result);

        return result;
    }

}

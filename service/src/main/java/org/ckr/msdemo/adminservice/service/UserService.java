package org.ckr.msdemo.adminservice.service;

import com.google.common.base.Strings;
import org.ckr.msdemo.adminservice.annotation.ReadOnlyTransaction;
import org.ckr.msdemo.adminservice.annotation.ReadWriteTransaction;
import org.ckr.msdemo.adminservice.entity.UserRole;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.repository.UserRoleRepository;
import org.ckr.msdemo.adminservice.repository.UserRepository;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserQueryView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.adminservice.valueobject.UserWithRole;
import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleService roleService;

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

        for (UserRole role : user.getRoles()) {

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
        List<UserRole> roles = new ArrayList<UserRole>();

        validateUserInfo(userForm);

        user.setUserName(userForm.getUserName());
        user.setUserDescription(userForm.getUserDescription());
        user.setLocked(Boolean.FALSE);
        user.setPassword(encodePassword(userForm.getPassword()));

        if (!CollectionUtils.isEmpty(userForm.getRoles())) {
            for (UserServiceForm.RoleServiceForm roleForm : userForm.getRoles()) {
                UserRole userRole = userRoleRepository.findByRoleCode(roleForm.getRoleCode());
                if (userRole != null){
                    roles.add(userRole);
                }
            }
        }

        user.setRoles(roles);
        this.userRepository.save(user);
    }

    /**
     * Update the role of user
     *
     * @param userName
     * @param roles
     */
    public void updateUserRole(String userName, List<UserServiceForm.RoleServiceForm> roles){
        User user = userRepository.findByUserName(userName);

        List<String> auditLogList = new ArrayList<>();

        List<UserRole> updatedRoles = new ArrayList<UserRole>();
        ApplicationException applicationException = new ApplicationException("exceptions for user role update");
        if (user == null) {
            applicationException.addMessage("security.maintain_user.not_existing_user", new Object[]{userName});
        } else {
            validateRoleInfos(roles, applicationException);
            applicationException.throwThisIfValid();
            for (UserServiceForm.RoleServiceForm roleForm : roles) {
                UserRole userRole = userRoleRepository.findByRoleCode(roleForm.getRoleCode());
                if (userRole != null){
                    updatedRoles.add(userRole);
                }
            }
            user.setRoles(updatedRoles);
            userRepository.save(user);
        }
    }

    /**
     * Retrieve all roles of a user
     *
     * @param userName user name
     * @return
     */
    public List<UserRole> getUserRole(String userName){
        User user = userRepository.findByUserName(userName);
        ApplicationException applicationException = new ApplicationException("exceptions for getting user role");
        if (user == null){
            applicationException.addMessage("security.maintain_user.not_existing_user", new Object[]{userName});
            applicationException.throwThisIfValid();
        }else{
            return user.getRoles();
        }
        return null;
    }

    /**
     * Validate user information according to UserServiceForm.
     * <li>user name is not empty
     * <li>user description is not empty
     *
     * @param userForm UserServiceForm
     */
    private void validateUserInfo(UserServiceForm userForm) {

        ApplicationException applicationException =
                new ApplicationException("exceptions for user creation.");

        if (Strings.isNullOrEmpty(userForm.getUserName())) {
            applicationException.addMessage("security.maintain_user.user_name_empty");
        }

        if (Strings.isNullOrEmpty(userForm.getUserDescription())) {
            applicationException.addMessage("security.maintain_user.user_desc_empty");
        }

        if (!Strings.isNullOrEmpty(userForm.getUserName()) &&
            this.userRepository.findByUserName(userForm.getUserName()) != null) {
            applicationException.addMessage("security.maintain_user.duplicated_user");
        }
        validateRoleInfos(userForm.getRoles(), applicationException);

        applicationException.throwThisIfValid();
    }

    private void validateRoleInfos(List<UserServiceForm.RoleServiceForm> roleServiceForms, ApplicationException applicationException) {
        if (!CollectionUtils.isEmpty(roleServiceForms)) {
            for (UserServiceForm.RoleServiceForm roleForm : roleServiceForms) {
                this.validateRoleInfo(roleForm, applicationException);
            }
        }
    }

    private void validateRoleInfo(UserServiceForm.RoleServiceForm roleServiceForm, ApplicationException applicationException) {
        UserRole userRole = userRoleRepository.findByRoleCode(roleServiceForm.getRoleCode());
        if (userRole == null){
            applicationException.addMessage("security.maintain_role.not_existing_role", new Object[] {roleServiceForm.getRoleCode()});
        }
    }

    /**
     * Need to be implemented
     * @param pwd  original password.
     * @return     encoded password.
     */
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

        LOG.debug("user ID is {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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

        if (!Strings.isNullOrEmpty(userName)) {
            params.put("userName", userName);
        }

        if (!Strings.isNullOrEmpty(userDesc)) {
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

    @ReadWriteTransaction
    public void deleteUser(String userName) {
        userRepository.delete(userName);

    }

    public void readFile(String fileName) {

        File file = new File(fileName);
        try {
            FileReader reader = new FileReader(file);
        } catch (FileNotFoundException e) {

        }
    }



    @Override
    public int hashCode() {
        return Objects.hash(userRepository);
    }
}

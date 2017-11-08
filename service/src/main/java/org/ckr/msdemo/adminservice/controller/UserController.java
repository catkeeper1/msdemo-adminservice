package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.adminservice.constant.FunctionPointConstant;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.entity.UserRole;
import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserQueryView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.adminservice.valueobject.UserWithRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST api for User related information.
 *
 * @see UserService
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**
     * Query user detail info by user ID.
     *
     * @param userName user name to get
     * @return UserDetailView
     * @see UserService#getUser(String)
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public UserDetailView getUser(@PathVariable("userId") String userName) {
        return this.userService.getUser(userName);
    }

    /**
     * Query all users info.
     *
     * @return return all users
     * @see UserService#queryUsers2(String, String)
     */
    @RequestMapping(value = "/user/queryUser", method = RequestMethod.GET)
    public List<UserQueryView> getUsers() {
        return this.userService.queryUsers2(null, null);
    }

    /**
     * Create user by {@link UserServiceForm} specified.
     *
     * @param user user info specify by {@link UserServiceForm}
     * @return always return true
     * @see UserService#createUser(UserServiceForm)
     */
    @RequestMapping(value = "/user/createUser", method = RequestMethod.POST)
    public Boolean createUser(@RequestBody UserServiceForm user) {
        this.userService.createUser(user);
        return Boolean.TRUE;
    }

    /**
     * Update user role with roles specified.
     *
     * @param userName user name
     * @param roles roles of the user
     * @return
     */
    @RequestMapping(value = "/user/{userName}", method = RequestMethod.POST)
    public Boolean updateUserRole(@PathVariable("userName") String userName, @RequestBody List<UserServiceForm.RoleServiceForm> roles){
        this.userService.updateUserRole(userName, roles);
        return Boolean.TRUE;
    }

    /**
     * Query all users info.
     *
     * @return return all users
     * @see UserService#queryUsers2(String, String)
     */
    @RequestMapping(value = "/user/queryUsers", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(this, '"+FunctionPointConstant.QUERY_ALL_USERS+"')")
    public List<User> queryUsers() {
        return userService.queryUsers();
    }

    /**
     * Query user by specify user name and user description.
     *
     * @param userName name of user
     * @param userDesc description of user
     * @return user info matched condition
     * @see UserService#queryUsers2(String, String)
     */
    @RequestMapping(value = "/user/queryUsers2", method = RequestMethod.GET)
    public List<UserQueryView> queryUser2(@RequestParam("userName") String userName,
                                          @RequestParam("userDesc") String userDesc) {
        return userService.queryUsers2(userName, userDesc);
    }

    /**
     * Query user by specify user name and user description.
     *
     * @param userName name of user
     * @param userDesc description of user
     * @return user info matched condition (one role per record)
     * @see UserService#queryUsersWithRoles(String, String)
     */
    @RequestMapping(value = "/user/queryUsersWithRoles", method = RequestMethod.GET)
    public List<UserWithRole> queryUserWithRoles(@RequestParam String userName, @RequestParam String userDesc) {
        return userService.queryUsersWithRoles(userName, userDesc);
    }

    @RequestMapping(value = "user/{userName}", method = RequestMethod.DELETE)
    public Boolean deleteUser(@PathVariable(name = "userName") String userName){
        userService.deleteUser(userName);
        return true;
    }

}

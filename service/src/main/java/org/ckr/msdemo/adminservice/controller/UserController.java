package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.adminservice.constant.FunctionPointConstant;
import org.ckr.msdemo.adminservice.entity.User;
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
 * Provide HTTP endpoints for user maintenance functions.
 *
 * @see UserService
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**
     * Query user detail info by user ID. It should be used by screen that show
     * the detail info of one user. Such as, detail user query screen or user detail maintain screen.
     * It should call {@link UserService#queryUser(String)}.
     *
     * @param userName user name to get
     * @return UserDetailView
     * @see UserService#queryUser(String)
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public UserDetailView queryUserById(@PathVariable("userId") String userName) {
        return this.userService.queryUser(userName);
    }

    /**
     * Query all users info.
     *
     * @return return all users
     * @see UserService#queryUsers(String, String)
     */
    @RequestMapping(value = "/user/queryUser", method = RequestMethod.GET)
    public List<UserQueryView> queryAllUsers() {
        return this.userService.queryUsers(null, null);
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
    public Boolean updateUserRole(@PathVariable("userName") String userName, @RequestBody List<String> roles) {
        this.userService.updateUserRole(userName, roles);
        return Boolean.TRUE;
    }



    /**
     * Query user by specify user name and user description.
     *
     * @param userName name of user
     * @param userDesc description of user
     * @return user info matched condition
     * @see UserService#queryUsers(String, String)
     */
    @RequestMapping(value = "/user/queryUsers", method = RequestMethod.GET)
    public List<UserQueryView> queryUser(@RequestParam("userName") String userName,
                                          @RequestParam("userDesc") String userDesc) {
        return userService.queryUsers(userName, userDesc);
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
    public Boolean deleteUser(@PathVariable(name = "userName") String userName) {
        userService.deleteUser(userName);
        return true;
    }

}

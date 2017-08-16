package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.adminservice.vo.UserWithRole;
import org.ckr.msdemo.adminservice.valueobject.UserQueryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yukai.a.lin on 8/9/2017.
 */
@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/queryUsers", method = RequestMethod.GET)
    public List<User> queryUser() {
        return userService.queryUsers();
    }

    @RequestMapping(value = "/user/queryUsers2", method = RequestMethod.GET)
    public List<UserQueryView> queryUser2(@RequestParam String userName, @RequestParam String userDesc) {
        return userService.queryUsers2(userName, userDesc);

    }

    @RequestMapping(value = "/user/queryUsersWithRole", method = RequestMethod.GET)
    public List<User> queryUserWithRole(@RequestParam String userName, @RequestParam String userDesc) {
        return userService.queryUsersWithRole(userName, userDesc);

    }

    @RequestMapping(value = "/user/queryUsersWithRoles", method = RequestMethod.GET)
    public List<UserWithRole> queryUserWithRoles(@RequestParam String userName, @RequestParam String userDesc) {
        return userService.queryUsersWithRoles(userName, userDesc);

    }

}

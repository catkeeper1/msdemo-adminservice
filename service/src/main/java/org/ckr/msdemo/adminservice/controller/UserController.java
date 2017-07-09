package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.pagination.PaginationContext;
import org.ckr.msdemo.pagination.PaginationSupported;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Query user detail info by user ID.
     * @see UserService#getUser(String)
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public UserDetailView getUser(@PathVariable("userId") String userName) {

        return this.userService.getUser(userName);

    }


    @RequestMapping(value = "/user/queryUser", method = RequestMethod.GET)
    @PaginationSupported
    public List<UserDetailView> getUsers() {

        PaginationContext.getQueryRequest();

        PaginationContext.setResponseInfo(10L, 98L, 1000L);

        return new ArrayList<>();

    }
}

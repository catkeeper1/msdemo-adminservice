package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.pagination.PaginationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        PaginationContext.QueryRequest queryRequest = PaginationContext.getQueryRequest();
        return userService.queryUsers(queryRequest);
    }

}

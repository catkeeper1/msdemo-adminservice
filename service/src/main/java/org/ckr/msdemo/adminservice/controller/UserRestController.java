package org.ckr.msdemo.adminservice.controller;

import com.google.gson.GsonBuilder;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.pagination.PaginationContext;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
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
    public PaginationContext.QueryResponse queryUser2(@RequestParam String userName, @RequestParam String userDesc) {
        PaginationContext.QueryResponse queryResponse = userService.queryUsers2(userName, userDesc);
        return queryResponse;
    }

}

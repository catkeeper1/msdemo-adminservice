package org.ckr.msdemo.adminservice.controller;


import org.ckr.msdemo.adminservice.annotation.ReadOnlyTransaction;
import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    @ReadOnlyTransaction
    public UserDetailView getUser(@PathVariable("userId") String userName) {

        return this.userService.getUser(userName);

    }
}

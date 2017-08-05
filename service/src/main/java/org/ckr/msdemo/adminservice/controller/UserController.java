package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.adminservice.service.UserService;
import org.ckr.msdemo.adminservice.spec.UserSpecification;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.pagination.PaginationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController implements UserSpecification {

    @Autowired
    UserService userService;


    /**
     * Query user detail info by user ID.
     *
     * @see UserService#getUser(String)
     */
    @Override
    public UserDetailView getUser(String userName) {

        return this.userService.getUser(userName);

    }


    @Override
    public List<UserDetailView> getUsers() {

        PaginationContext.getQueryRequest();

        PaginationContext.setResponseInfo(10L, 98L, 1000L);
        UserDetailView v = new UserDetailView();
        v.setUserName("abc");
        List<UserDetailView> result = new ArrayList<>();
        //result.add(v);

        return result;

    }

    @Override
    public Boolean createUser(UserServiceForm user) {

        this.userService.createUser(user);


        return Boolean.TRUE;
    }

}

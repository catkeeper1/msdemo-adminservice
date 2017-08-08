package org.ckr.msdemo.adminservice.spec;

import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */
public interface UserSpecification {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    UserDetailView getUser(@PathVariable("userId") String userName);

    @RequestMapping(value = "/user/queryUser", method = RequestMethod.GET)
    List<UserDetailView> getUsers();

    @RequestMapping(value = "/user/createUser", method = RequestMethod.POST)
    Boolean createUser(@RequestBody UserServiceForm user);
}

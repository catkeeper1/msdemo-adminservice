package org.ckr.msdemo.adminservice.client;

import static org.ckr.msdemo.adminservice.client.ServiceNameConstant.ADMIN_SERVICE;
import static org.ckr.msdemo.adminservice.client.ServiceNameConstant.ADMIN_SERVICE_CONTEXT;

import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserQueryView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */
@FeignClient(value = ADMIN_SERVICE, path = ADMIN_SERVICE_CONTEXT)
public interface UserClient {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    UserDetailView queryUserById(@PathVariable("userId") String userName);

    @RequestMapping(value = "/user/queryUser", method = RequestMethod.GET)
    List<UserQueryView> queryAllUsers();

    @RequestMapping(value = "/user/createUser", method = RequestMethod.POST)
    Boolean createUser(@RequestBody UserServiceForm user);

    @RequestMapping(value = "/user/{userName}", method = RequestMethod.POST)
    Boolean updateUserRole(@PathVariable("userName") String userName, @RequestBody List<String> roles);

    @RequestMapping(value = "user/{userName}", method = RequestMethod.DELETE)
    Boolean deleteUser(@PathVariable(name = "userName") String userName);

}

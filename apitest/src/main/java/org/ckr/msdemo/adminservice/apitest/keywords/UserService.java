package org.ckr.msdemo.adminservice.apitest.keywords;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    UserClient userClient;

    public UserService() {
        this.userClient = (UserClient) BeanContainer.getBean(UserClient.class);
    }

    public void getUser(String userName) {

        try {
            UserDetailView userDetailView = this.userClient.getUser(userName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static void main (String args[]){
        UserService userService = new UserService();
        userService.getUser("ABC");
    }
}

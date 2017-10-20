package org.ckr.msdemo.adminservice.apitest.keywords;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;

public class UserService {
    UserClient userClient;

    public UserService(){
        this.userClient = (UserClient) BeanContainer.getBean(UserClient.class);
    }

    public void getUser(String userName) {

        try {
            UserDetailView userDetailView = this.userClient.getUser(userName);
            Gson gson = new GsonBuilder().create();
            System.out.println("*info* " + gson.toJson(userDetailView));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}

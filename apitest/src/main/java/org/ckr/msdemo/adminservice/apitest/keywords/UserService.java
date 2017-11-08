package org.ckr.msdemo.adminservice.apitest.keywords;

import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    public void createUser(String userName, String userDescription, String password, Boolean locked) {
        UserServiceForm userServiceForm = new UserServiceForm();
        userServiceForm.setUserName(userName);
        userServiceForm.setUserDescription(userDescription);
        userServiceForm.setPassword(password);
        userServiceForm.setLocked(locked);
        this.userClient.createUser(userServiceForm);
    }

    public void updateRoleToUser(String userName, String... roles) {
        List<UserServiceForm.RoleServiceForm> roleServiceForms = new ArrayList<UserServiceForm.RoleServiceForm>();
        for (String roleCode :
                roles) {
            UserServiceForm.RoleServiceForm roleServiceForm = new UserServiceForm.RoleServiceForm();
            roleServiceForm.setRoleCode(roleCode);
            roleServiceForms.add(roleServiceForm);
        }
        this.userClient.updateUserRole(userName, roleServiceForms);
    }

    public void deleteUser(String userName) {
        this.userClient.deleteUser(userName);
    }

    public static void main(String args[]) {
        UserService userService = new UserService();
        userService.getUser("ABC");
    }
}

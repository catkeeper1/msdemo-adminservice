package org.ckr.msdemo.adminservice.apitest.keywords;

import org.ckr.msdemo.adminservice.apitest.BeanContainer;
import org.ckr.msdemo.adminservice.client.UserClient;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserKeyword {
    private static final Logger LOG = LoggerFactory.getLogger(UserKeyword.class);

    UserClient userClient;

    public UserKeyword() {
        this.userClient = (UserClient) BeanContainer.getBean(UserClient.class);
    }

    public void queryUser(String userName) {


        UserDetailView userDetailView = this.userClient.getUser(userName);

        assertThat(userDetailView.getUserName()).isEqualTo(userName);

    }

    public void createUser(String userName, String userDescription, String password, Boolean locked) {
        UserServiceForm userServiceForm = new UserServiceForm();
        userServiceForm.setUserName(userName);
        userServiceForm.setUserDescription(userDescription);
        userServiceForm.setPassword(password);
        userServiceForm.setLocked(locked);
        this.userClient.createUser(userServiceForm);
    }

    public void updateRoleToUser(String userName, String roleCode) {

        System.out.println("*INFO* " + "update user role . User name = " + userName + " role = " + roleCode);

        List<UserServiceForm.RoleServiceForm> roleServiceForms = new ArrayList<UserServiceForm.RoleServiceForm>();

        UserServiceForm.RoleServiceForm roleServiceForm = new UserServiceForm.RoleServiceForm();
        roleServiceForm.setRoleCode(roleCode);
        roleServiceForms.add(roleServiceForm);

        this.userClient.updateUserRole(userName, roleServiceForms);

        System.out.println("*INFO* " + "retrieve user detail to verify whether role has been updated successfully");
        UserDetailView userDetail = this.userClient.getUser(userName);

        assertThat(userDetail).isNotNull();

        assertThat(userDetail.getRoles().size()).isGreaterThan(0);

        boolean roleFound = false;
        for(UserServiceForm.RoleServiceForm roleForm: userDetail.getRoles()) {
            if (roleForm.getRoleCode().equals(roleCode)) {
                roleFound = true;
            }
        }
        assertThat(roleFound)
                .as("User role {} is not added successfully.", roleCode)
                .isTrue();
    }

    public void deleteUser(String userName) {
        this.userClient.deleteUser(userName);
    }


}

package org.ckr.msdemo.adminservice.service;

import org.ckr.msdemo.adminservice.annotation.ReadOnlyTransaction;
import org.ckr.msdemo.adminservice.entity.Role;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.repository.UserRepository;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @ReadOnlyTransaction
    public UserDetailView getUser(String userName) {

        UserDetailView result = new UserDetailView();

        User user = this.userRepository.findByUserName(userName);

        if(user == null) {
            ApplicationException exp =  new ApplicationException("security.maintain_user.not_existing_user",
                                           new Object[] {userName});

            exp.addMessage("security.maintain_user.user_name_empty", null);

            throw exp;
        }

        result.setUserName(user.getUserName());
        result.setUserDescription(user.getUserDescription());
        result.setLocked(user.getLocked());

        List<UserServiceForm.RoleServiceForm> roleList = new ArrayList<>();

        for (Role role : user.getRoles()) {

            UserDetailView.RoleDetailView roleView = new UserDetailView.RoleDetailView();

            roleView.setRoleCode(role.getRoleCode());
            roleView.setRoleDescription(role.getRoleDescription());

            roleList.add(roleView);
        }

        result.setRoles(roleList);

        return result;
    }
}

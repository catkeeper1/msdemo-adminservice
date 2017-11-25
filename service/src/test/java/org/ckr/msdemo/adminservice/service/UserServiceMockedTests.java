package org.ckr.msdemo.adminservice.service;

import com.google.common.base.Strings;
import mockit.Expectations;
import mockit.Verifications;
import org.ckr.msdemo.adminservice.TestUtil;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.entity.UserRole;
import org.ckr.msdemo.adminservice.entity.UserToUserRoleMap;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.exception.ApplicationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class UserServiceMockedTests extends UserServiceMockedTestsBase{

    @Test
    public void testQueryUser_userNotExist() {

        new Expectations() {{
            userRepository.findByUserName("userA");
            result = null;

        }};

        try {
            this.userService.queryUser("userA");
        } catch (ApplicationException ae) {
            TestUtil.checkErrorMsg(ae,
                                  "security.maintain_user.not_existing_user",
                                   "The user userA is not exist.");
            return;
        }

        fail("exception is expected.");

    }

    @Test
    public void testQueryUser_successfully() {
        User user = new User();
        user.setUserName("userA");

        UserRole role = new UserRole();
        role.setRoleCode("roleA");

        user.setRoles(newArrayList(role));

        new Expectations() {{
            userRepository.findByUserName("userA");
            result = user;

        }};


        UserDetailView userView = this.userService.queryUser("userA");


        assertThat(userView.getUserName()).isEqualTo("userA");

        assertThat(userView.getRoles().get(0).getRoleCode()).isEqualTo("roleA");

    }

    @Test
    public void testCreateUser_successfully() {

        UserServiceForm form = new UserServiceForm();

        form.setUserName("userName");
        form.setUserDescription("userDescrption");
        form.setLocked(Boolean.TRUE);
        form.setPassword("password");

        List<UserServiceForm.RoleServiceForm> roleForms = new ArrayList<>();

        UserServiceForm.RoleServiceForm roleForm = new UserServiceForm.RoleServiceForm();
        roleForm.setRoleCode("role_a");
        roleForms.add(roleForm);

        roleForm = new UserServiceForm.RoleServiceForm();
        roleForm.setRoleCode("role_b");
        roleForms.add(roleForm);

        form.setRoles(roleForms);

        new Expectations() {{
            userRepository.findByUserName(anyString);
            result = null;

            UserRole userRole = new UserRole();
            userRole.setRoleCode("role_a");
            userRoleRepository.findByRoleCode("role_a");
            result = userRole;

            userRole = new UserRole();
            userRole.setRoleCode("role_b");
            userRoleRepository.findByRoleCode("role_b");
            result = userRole;



        }};

        this.userService.createUser(form);

        new Verifications() {{
            User user;
            userRepository.save(user = withCapture());
            times = 1;

            assertThat(user.getUserName()).isEqualTo(form.getUserName());
            assertThat(user.getUserDescription()).isEqualTo(form.getUserDescription());
            assertThat(user.getLocked()).isEqualTo(Boolean.FALSE);
            assertThat(user.getPassword()).isNotNull();

            UserToUserRoleMap userToUserRoleMap;
            userToUserRoleMapRepository.save(userToUserRoleMap = withCapture());
            times = form.getRoles().size();

            assertThat(userToUserRoleMap.getPk().getUserName()).isEqualTo(form.getUserName());

            assertThat(userToUserRoleMap.getPk().getRoleCode())
                    .isIn("role_a", "role_b");


        }};
    }

    /**
     * This is only a demo about partial mocking. In real project, this approach should be used if logic is too
     * complicated and too many things need to mocked. In this example, partial mocking is used to mocked some private
     * method so that the repository that called by those private methods do not need to be mocked.
     */
    @Test
    public void testCreateUser_successfully2() {

        final UserServiceForm form = new UserServiceForm();

        form.setUserName("userName");
        form.setUserDescription("userDescrption");
        form.setLocked(Boolean.TRUE);
        form.setPassword("password");

        List<UserServiceForm.RoleServiceForm> roleForms = new ArrayList<>();

        UserServiceForm.RoleServiceForm roleForm = new UserServiceForm.RoleServiceForm();
        roleForm.setRoleCode("role_a");
        roleForms.add(roleForm);

        roleForm = new UserServiceForm.RoleServiceForm();
        roleForm.setRoleCode("role_b");
        roleForms.add(roleForm);

        form.setRoles(roleForms);


        //mock validateUserInfoForCreate so that do not need to mock the repositories used by it.
        new Expectations(UserService.class) {{
           userService.validateUserInfoForCreate((form));
           times = 1;
        }};

        this.userService.createUser(form);

        new Verifications() {{
            User user;
            userRepository.save(user = withCapture());
            times = 1;

            assertThat(user.getUserName()).isEqualTo(form.getUserName());
            assertThat(user.getUserDescription()).isEqualTo(form.getUserDescription());
            assertThat(user.getLocked()).isEqualTo(Boolean.FALSE);
            assertThat(user.getPassword()).isNotNull();

            UserToUserRoleMap userToUserRoleMap;
            userToUserRoleMapRepository.save(userToUserRoleMap = withCapture());
            times = form.getRoles().size();

            assertThat(userToUserRoleMap.getPk().getUserName()).isEqualTo(form.getUserName());

            assertThat(userToUserRoleMap.getPk().getRoleCode())
                    .isIn("role_a", "role_b");

        }};
    }

    private void doTestCreateUserValidation(String userName,
                                            String userDescription,
                                            User previosuUser,
                                            String messageCode,
                                            String message) {

        UserServiceForm form = new UserServiceForm();

        form.setUserName(userName);
        form.setUserDescription(userDescription);


        if(!Strings.isNullOrEmpty(userName)) {
            new Expectations() {{
                userRepository.findByUserName(userName);
                result = previosuUser;
            }};
        }

        try {
            this.userService.createUser(form);
        } catch (ApplicationException ae) {
            ae.printStackTrace();
            TestUtil.checkErrorMsg(ae, messageCode, message);
            return;
        }

        fail("ApplicationException is expected.");
    }

    @Test
    public void testCreateUser_userNameIsEmpty() {

        doTestCreateUserValidation("",
                                   "desc",
                                   null,
                                   "security.maintain_user.user_name_empty",
                                   "User name cannot be empty.");

    }

    @Test
    public void testCreateUser_userDescriptionIsEmpty() {

        doTestCreateUserValidation("userName",
                "",
                null,
                "security.maintain_user.user_desc_empty",
                "User description cannot be empty.");

    }

    @Test
    public void testCreateUser_duplicatedUser() {

        doTestCreateUserValidation("userName",
                "",
                new User(),
                "security.maintain_user.duplicated_user",
                "The user name userName is duplicated with an existing one.");

    }


    /**
     * Test the validation logic for createUser method.
     */
    @RunWith(Parameterized.class)
    public static class CreateUserValidationMockedTests extends UserServiceMockedTestsBase {

        private String userName;
        private String userDescription;
        private User previousUser;
        private String messageCode;
        private String message;

        public CreateUserValidationMockedTests(String userName,
                                               String userDescription,
                                               User previousUser,
                                               String messageCode,
                                               String message) {
            this.userName = userName;
            this.userDescription = userDescription;
            this.previousUser = previousUser;
            this.messageCode = messageCode;
            this.message = message;
        }

        @Parameterized.Parameters
        public static Collection testParams() {
            return Arrays.asList(new Object[][] {
                //user name    user description   previous user   exception message code
                //exception message
                { ""         , "userDescription", null      ,     "security.maintain_user.user_name_empty",
                 "User name cannot be empty."},
                { "userName" , ""               , null      ,     "security.maintain_user.user_desc_empty",
                 "User description cannot be empty."},
                { "userName" , "userDesc"       , new User(),     "security.maintain_user.duplicated_user",
                 "The user name userName is duplicated with an existing one."}
            });
        }

        @Test
        public void testCreateUser_withErrorMessage() {

            UserServiceForm form = new UserServiceForm();

            form.setUserName(this.userName);
            form.setUserDescription(this.userDescription);

            final CreateUserValidationMockedTests toThis = this;

            if(!Strings.isNullOrEmpty(this.userName)) {
                new Expectations() {{
                    userRepository.findByUserName(toThis.userName);
                    result = toThis.previousUser;
                }};
            }

            try {
                this.userService.createUser(form);
            } catch (ApplicationException ae) {
                TestUtil.checkErrorMsg(ae, messageCode, message);

                return;
            }

            fail("ApplicationException is expected.");

        }
    }

    @Test
    public void testupdateUserRole_successfully() {

        String userName = "userA";
        List<String> roles = newArrayList("roleA", "roleB");


        new Expectations() {{
            userRepository.findByUserName(userName);
            result = new User();

            userToUserRoleMapRepository.deleteByPkUserName(userName);
            times = 1;


        }};

        userService.updateUserRole(userName, roles);

        new Verifications() {{
            User user;
            userRepository.save(user = withCapture());
            times = 1;


            UserToUserRoleMap userToUserRoleMap;
            userToUserRoleMapRepository.save(userToUserRoleMap = withCapture());
            times = roles.size();


            assertThat(userToUserRoleMap.getPk().getRoleCode())
                    .isIn(roles);


        }};

    }
}

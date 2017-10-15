package org.ckr.msdemo.adminservice.service;

import com.google.common.base.Strings;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.repository.UserRepository;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by Administrator on 2017/10/15.
 */
public class UserServiceMockedTests {

    @Tested
    private UserService userService;

    @Injectable
    private UserRepository userRepository;

    @Injectable
    private JpaRestPaginationService jpaRestPaginationService;

    @Test
    public void testCreateUser_successfully() {

        UserServiceForm form = new UserServiceForm();

        form.setUserName("userName");
        form.setUserDescription("userDescrption");
        form.setLocked(Boolean.TRUE);
        form.setPassword("password");

        new Expectations() {{
            userRepository.findByUserName(anyString);
            result = null;

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
        }};
    }


    /**
     * Test the validation logic for createUser method.
     */
    @RunWith(Parameterized.class)
    public static class CreateUserValidationMockedTests {

        @Tested
        private UserService userService;

        @Injectable
        private UserRepository userRepository;

        @Injectable
        private JpaRestPaginationService jpaRestPaginationService;

        private String userName;
        private String userDescription;
        private User previousUser;
        private String messageCode;

        public CreateUserValidationMockedTests(String userName,
                                               String userDescription,
                                               User previousUser,
                                               String messageCode) {
            this.userName = userName;
            this.userDescription = userDescription;
            this.previousUser = previousUser;
            this.messageCode = messageCode;
        }

        @Parameterized.Parameters
        public static Collection testParams() {
            return Arrays.asList(new Object[][] {
                //user name    user description   previous user   exception message code
                { ""         , "userDescription", null      ,     "security.maintain_user.user_name_empty" },
                { "userName" , ""               , null      ,     "security.maintain_user.user_desc_empty"},
                { "userName" , "userDesc"       , new User(),     "security.maintain_user.duplicated_user"}
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
                assertThat(ae.getMessageList().size()).isGreaterThan(0);
                assertThat(ae.getMessageList()).contains(
                    new ApplicationException.ExceptionMessage(toThis.messageCode, (Object[]) null)
                );

                return;
            }

            fail("ApplicationException is expected.");

        }
    }

}

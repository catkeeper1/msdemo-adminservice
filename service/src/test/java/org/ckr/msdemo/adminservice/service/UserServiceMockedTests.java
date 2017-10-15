package org.ckr.msdemo.adminservice.service;

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

    @Test
    public void testCreateUser_userAlreadyExist() {

        UserServiceForm form = new UserServiceForm();

        form.setUserName("userName");
        form.setUserDescription("userDescrption");
        form.setLocked(Boolean.TRUE);
        form.setPassword("password");

        new Expectations() {{
            userRepository.findByUserName("userName");
            result = new User();

        }};

        try {
            this.userService.createUser(form);
        } catch (ApplicationException ae) {
            assertThat(ae.getMessageList().size()).isEqualTo(1);
            assertThat(ae.getMessageList().get(0).getMessageCode())
                         .isEqualTo("security.maintain_user.duplicated_user");

            return;
        }

        fail("ApplicationException is expected.");


    }
}

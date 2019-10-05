package org.ckr.msdemo.adminservice.service;




import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import org.ckr.msdemo.adminservice.TestUtil;
import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.entity.UserRole;
import org.ckr.msdemo.adminservice.entity.UserToUserRoleMap;
import org.ckr.msdemo.adminservice.repository.UserRepository;
import org.ckr.msdemo.adminservice.repository.UserRoleRepository;
import org.ckr.msdemo.adminservice.repository.UserToUserRoleMapRepository;
import org.ckr.msdemo.adminservice.valueobject.UserDetailView;
import org.ckr.msdemo.adminservice.valueobject.UserServiceForm;
import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.*;

/**
 * Created by Administrator on 10/5/2019.
 */
//@RunWith(PowerMockRunner.class)
public class UserServiceMOMockedTests {

    @InjectMocks
    protected UserService userService;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected UserRoleRepository userRoleRepository;

    @Mock
    protected UserToUserRoleMapRepository userToUserRoleMapRepository;

    @Mock
    protected JpaRestPaginationService jpaRestPaginationService;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void testQueryUser_userNotExist() {
        when(userRepository.findByUserName("userA")).thenReturn(null);

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

        when(userRepository.findByUserName("userA")).thenReturn(user);

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

        when(userRepository.findByUserName(any())).thenReturn(null);

        UserRole userRole = new UserRole();
        userRole.setRoleCode("role_a");

        when(userRoleRepository.findByRoleCode("role_a")).thenReturn(userRole);

        userRole = new UserRole();
        userRole.setRoleCode("role_b");
        when(userRoleRepository.findByRoleCode("role_b")).thenReturn(userRole);

        this.userService.createUser(form);

        User user;
        ArgumentCaptor<User> usrArgument = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(usrArgument.capture());
        user = usrArgument.getValue();

        assertThat(user.getUserName()).isEqualTo(form.getUserName());
        assertThat(user.getUserDescription()).isEqualTo(form.getUserDescription());
        assertThat(user.getLocked()).isEqualTo(Boolean.FALSE);
        assertThat(user.getPassword()).isNotNull();

        UserToUserRoleMap userToUserRoleMap;
        ArgumentCaptor<UserToUserRoleMap> roleMapArgument = ArgumentCaptor.forClass(UserToUserRoleMap.class);
        verify(userToUserRoleMapRepository, times(form.getRoles().size())).save(roleMapArgument.capture());
        userToUserRoleMap = roleMapArgument.getValue();

        assertThat(userToUserRoleMap.getPk().getUserName()).isEqualTo(form.getUserName());

        assertThat(userToUserRoleMap.getPk().getRoleCode()).isIn("role_a", "role_b");

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
        UserService testedService = spy(userService);
        doNothing().when(testedService).validateUserInfoForCreate(form);


        testedService.createUser(form);

        verify(testedService, times(1)).validateUserInfoForCreate(form);

        User user;
        ArgumentCaptor<User> usrArgument = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(usrArgument.capture());
        user = usrArgument.getValue();

        assertThat(user.getUserName()).isEqualTo(form.getUserName());
        assertThat(user.getUserDescription()).isEqualTo(form.getUserDescription());
        assertThat(user.getLocked()).isEqualTo(Boolean.FALSE);
        assertThat(user.getPassword()).isNotNull();

        UserToUserRoleMap userToUserRoleMap;
        ArgumentCaptor<UserToUserRoleMap> roleMapArgument = ArgumentCaptor.forClass(UserToUserRoleMap.class);
        verify(userToUserRoleMapRepository, times(form.getRoles().size())).save(roleMapArgument.capture());
        userToUserRoleMap = roleMapArgument.getValue();

        assertThat(userToUserRoleMap.getPk().getUserName()).isEqualTo(form.getUserName());

        assertThat(userToUserRoleMap.getPk().getRoleCode())
                    .isIn("role_a", "role_b");

    }

}

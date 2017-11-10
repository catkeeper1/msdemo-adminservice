package org.ckr.msdemo.adminservice.service;

import mockit.Injectable;
import mockit.Tested;
import org.ckr.msdemo.adminservice.repository.UserRepository;
import org.ckr.msdemo.adminservice.repository.UserRoleRepository;
import org.ckr.msdemo.pagination.JpaRestPaginationService;

/**
 * Created by Administrator on 2017/10/15.
 */
public class UserServiceMockedTestsBase {
    @Tested
    protected UserService userService;

    @Injectable
    protected UserRepository userRepository;

    @Injectable
    protected UserRoleRepository userRoleRepository;

    @Injectable
    protected RoleService roleService;

    @Injectable
    protected JpaRestPaginationService jpaRestPaginationService;
}

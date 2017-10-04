package org.ckr.msdemo.adminservice.dao;


import org.ckr.msdemo.adminservice.valueobject.UserWithRole;
import org.ckr.msdemo.utility.annotation.AssemblyTest;
import org.ckr.msdemo.utility.annotation.DbUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DbUnitTest
public class UserDaoDbTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void testQueryUsersWithRoles_WithUserNameOnly() {

        List<UserWithRole> resultList = userDao.queryUsersWithRoles("ABC", "");

        assertThat(resultList.size()).isEqualTo(3);
    }
}

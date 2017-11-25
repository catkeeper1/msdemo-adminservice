package org.ckr.msdemo.adminservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.ckr.msdemo.adminservice.entity.UserRole;
import org.ckr.msdemo.utility.annotation.DbUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@DbUnitTest
public class UserRoleRepositoryDbTests {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testfindExistingByRoleCode() {
        UserRole role = this.userRoleRepository.findByRoleCode("GROUP_ADMIN");
        assertThat(role.getRoleCode()).isEqualTo("GROUP_ADMIN");
    }


}

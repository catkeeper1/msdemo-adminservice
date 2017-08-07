package org.ckr.msdemo.adminservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.ckr.msdemo.adminservice.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class UserRoleRepositoryTests {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindUserRoleCode(){
        final PageRequest page = new PageRequest(
            0, 3, new Sort(
            new Sort.Order(Sort.Direction.DESC, "user_Name"))
        );
        List<User> users = this.userRoleRepository.findUsersByRoleCode("GROUP_ADMIN", page);
        assertThat(users.size()).isEqualTo(3);
        for (User user : users) {
            System.out.println("----**-----" + user.getUserName());
            assertThat(user.getUserName()).startsWith("ABC");
        }
    }
}

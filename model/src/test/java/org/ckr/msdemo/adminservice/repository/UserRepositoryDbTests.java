package org.ckr.msdemo.adminservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.entity.UserGroup;
import org.ckr.msdemo.adminservice.entity.UserRole;
import org.ckr.msdemo.utility.annotation.DbUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.LiquibaseConfiguration;
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
@DbUnitTest
public class UserRepositoryDbTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testfindExistingByUserName() {
        User user = this.userRepository.findByUserName("ABC");
        assertThat(user.getUserName()).isEqualTo("ABC");

        List<UserRole> roleList = user.getRoles();

        UserGroup userGroup = new UserGroup();

        userGroup.setGroupCode("test_group");

        this.userGroupRepository.save(userGroup);
        //this.userGroupRepository.flush();

        //user.setGroup(userGroup);
        //this.userRepository.save(user);

        //testEntityManager.flush();
        //testEntityManager.clear();
        user = this.userRepository.findByUserName(user.getUserName());

        this.userGroupRepository.findAll();

        userGroup = user.getGroup();

    }

    @Test
    public void testfindByUserName() {
        this.testEntityManager.persist(new User("xiaoming", "sb"));
        User user = this.userRepository.findByUserName("xiaoming");
        assertThat(user.getUserName()).isEqualTo("xiaoming");
        assertThat(user.getUserDescription()).isEqualTo("sb");
    }

    @Test
    public void testfindByUserNameAndLastModifiedTimestampGreaterThan() {
        User userPersist = new User("xiaoming", "sb");
        Date nowDate = new Date();
        Timestamp tenHoursEarlier = new Timestamp(System.currentTimeMillis() - 1000 * 3600 * 10);

        this.testEntityManager.persist(userPersist);
        User user = this.userRepository
            .findByUserNameAndUpdatedAtGreaterThanEqual("xiaoming", tenHoursEarlier);
        assertThat(user.getUserName()).isEqualTo("xiaoming");
        assertThat(user.getUserDescription()).isEqualTo("sb");

    }

    @Test
    public void testfindByUserNamePrefix() {
        this.testEntityManager.persist(new User("xiaoming", "sb"));
        this.testEntityManager.persist(new User("xiao", "sb"));
        this.testEntityManager.persist(new User("xia", "sb"));
        List<User> users = this.userRepository.findByUserNamePrefix("xiao");
        assertThat(users.size()).isEqualTo(2);
        for (User user : users) {
            assertThat(user.getUserName()).startsWith("xiao");
        }
    }

    @Test
    public void testfindByUserNamePrefix2() {

        final PageRequest page = new PageRequest(
            1, 5, new Sort(
            new Sort.Order(Sort.Direction.DESC, "userName"))
        );
        List<User> users = this.userRepository.findByUserNamePrefix("ABC", page);
        assertThat(users.size()).isEqualTo(2);
        for (User user : users) {
            System.out.println("----++-----" + user.getUserName());
            assertThat(user.getUserName()).startsWith("ABC");
        }
    }

    @Test
    public void testFindUsersByRoleCode() {
        final PageRequest page = new PageRequest(
                1, 1, new Sort(
                new Sort.Order(Sort.Direction.DESC, "USER_NAME"))
        );
        List<User> users = this.userRepository.findUsersByRoleCode("SECURITY_ADMIN", page);
        assertThat(users.size()).isEqualTo(1);
    }

}

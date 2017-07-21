package org.ckr.msdemo.adminservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.ckr.msdemo.adminservice.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.LiquibaseConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@TestPropertySource(
        properties = {
                "spring.jpa.hibernate.ddl-auto=none",
                "liquibase.change-log=classpath:db.changelog_adminservice_master.xml"
        }
)

public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Autowired
	private TestEntityManager testEntityManager;	
	
	@Test
	public void testfindExistingByUserName() {
		User user = this.userRepository.findByUserName("ABC");
		assertThat(user.getUserName()).isEqualTo("ABC");
		LiquibaseConfiguration springLiquibase ;
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
		Timestamp nowTimestamp = new Timestamp(nowDate.getTime());
		userPersist.setLastModifiedTimestamp(nowTimestamp);
		this.testEntityManager.persist(userPersist);
		User user = this.userRepository.findByUserNameAndLastModifiedTimestampGreaterThanEqual("xiaoming", nowTimestamp);
		assertThat(user.getUserName()).isEqualTo("xiaoming");
		assertThat(user.getUserDescription()).isEqualTo("sb");
		assertThat(user.getLastModifiedTimestamp()).isEqualTo(nowTimestamp);
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
}

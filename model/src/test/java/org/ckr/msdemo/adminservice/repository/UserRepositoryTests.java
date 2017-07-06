package org.ckr.msdemo.adminservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Timestamp;
import java.util.Date;

import org.assertj.core.util.DateUtil;
import org.ckr.msdemo.adminservice.entity.User;
import org.h2.util.DateTimeUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
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
}

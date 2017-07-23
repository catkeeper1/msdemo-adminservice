package org.ckr.msdemo.adminservice.repository;


import static org.assertj.core.api.Assertions.assertThat;

import org.ckr.msdemo.adminservice.entity.Role;
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
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;	
	
	@Test
	public void testfindExistingByRoleCode() {
		Role role = this.roleRepository.findByRoleCode("GROUP_ADMIN");
		assertThat(role.getRoleCode()).isEqualTo("GROUP_ADMIN");
	}
	
	
}

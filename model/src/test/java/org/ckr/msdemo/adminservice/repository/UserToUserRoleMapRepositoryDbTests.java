package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.UserToUserRoleMap;
import org.ckr.msdemo.utility.annotation.DbUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DbUnitTest
public class UserToUserRoleMapRepositoryDbTests {

    @Autowired
    UserToUserRoleMapRepository userToUserRoleMapRepository;

    private void createUserToUserRoleMapRecord(String userName, String roleCode) {

        UserToUserRoleMap map = new UserToUserRoleMap();
        UserToUserRoleMap.UserToUserRoleMapKey key = new UserToUserRoleMap.UserToUserRoleMapKey();

        key.setRoleCode(roleCode);
        key.setUserName(userName);
        map.setPk(key);

        this.userToUserRoleMapRepository.save(map);

    }

    @Test
    public void testDeleteByPkUserName() {

        createUserToUserRoleMapRecord("userA", "roleA");
        createUserToUserRoleMapRecord("userA", "roleB");
        createUserToUserRoleMapRecord("userB", "roleB");

        Long deletedRecords = this.userToUserRoleMapRepository.deleteByPkUserName("userA");
        assertThat(deletedRecords).isEqualTo(2L);

    }

}

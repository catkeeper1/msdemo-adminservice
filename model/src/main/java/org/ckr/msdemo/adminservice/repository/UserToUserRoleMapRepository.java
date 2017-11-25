package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.UserToUserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToUserRoleMapRepository extends JpaRepository<UserToUserRoleMap,
                                                                   UserToUserRoleMap.UserToUserRoleMapKey> {

    Long deleteByPkUserName(String userName);
}

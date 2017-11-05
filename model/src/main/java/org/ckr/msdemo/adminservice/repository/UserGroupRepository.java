package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.User;
import org.ckr.msdemo.adminservice.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/11/5.
 */
public interface UserGroupRepository extends JpaRepository<UserGroup, String> {
}

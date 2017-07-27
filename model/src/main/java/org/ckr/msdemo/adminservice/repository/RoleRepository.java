package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;


public interface RoleRepository extends CrudRepository<Role, String> {

    Role findByRoleCode(String roleCode);

    Role findByRoleCodeAndLastModifiedTimestampGreaterThanEqual(String roleCode, Timestamp lastModifiedTimestamp);


}
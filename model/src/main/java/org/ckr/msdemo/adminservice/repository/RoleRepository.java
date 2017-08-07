package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByRoleCode(String roleCode);

    Role findByRoleCodeAndLastModifiedTimestampGreaterThanEqual(String roleCode, Timestamp lastModifiedTimestamp);


}
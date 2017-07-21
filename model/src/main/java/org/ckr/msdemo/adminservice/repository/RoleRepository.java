package org.ckr.msdemo.adminservice.repository;

import java.sql.Timestamp;

import org.ckr.msdemo.adminservice.entity.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, String> {
	
    Role findByRoleCode(String roleCode);
    
    Role findByRoleCodeAndLastModifiedTimestampGreaterThanEqual(String roleCode, Timestamp LastModifiedTimestamp);
    
   

}
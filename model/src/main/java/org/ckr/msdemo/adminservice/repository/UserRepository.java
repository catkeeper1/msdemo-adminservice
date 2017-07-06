package org.ckr.msdemo.adminservice.repository;

import java.sql.Timestamp;

import org.ckr.msdemo.adminservice.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {
	
    User findByUserName(String userName);
    
    User findByUserNameAndLastModifiedTimestampGreaterThanEqual(String username, Timestamp LastModifiedTimestamp);

}
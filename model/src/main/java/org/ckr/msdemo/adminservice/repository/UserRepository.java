package org.ckr.msdemo.adminservice.repository;

import java.sql.Timestamp;
import java.util.List;

import org.ckr.msdemo.adminservice.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {
	
    User findByUserName(String userName);
    
    User findByUserNameAndLastModifiedTimestampGreaterThanEqual(String username, Timestamp LastModifiedTimestamp);
    
    @Query("select u from User u where u.userName like ?1%")
    List<User> findByUserNamePrefix(String usernamePrefix);

}
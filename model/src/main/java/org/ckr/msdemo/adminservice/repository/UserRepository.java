package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;


public interface UserRepository extends CrudRepository<User, String> {

    User findByUserName(String userName);

    User findByUserNameAndLastModifiedTimestampGreaterThanEqual(String username, Timestamp lastModifiedTimestamp);

    @Query("select u from User u where u.userName like ?1%")
    List<User> findByUserNamePrefix(String usernamePrefix);

}
package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;


public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user with user name specified.
     *
     * @param userName user name
     * @return user with user name specified.
     */
    User findByUserName(String userName);

    /**
     * Find user with user name and last modified time.
     *
     * @param username              user name
     * @param lastModifiedTimestamp last modified time
     * @return user with user name and last modified time
     */
    User findByUserNameAndUpdatedAtGreaterThanEqual(String username, Timestamp lastModifiedTimestamp);

    /**
     * find users whose name start with prefix specified with {@link Pageable}.
     *
     * @param usernamePrefix user name prefix
     * @param pageable       ordering and size limitation
     * @return users whose name start with prefix specified.
     */
    @Query("select u from User u where u.userName like ?1%")
    List<User> findByUserNamePrefix(String usernamePrefix, Pageable pageable);

    /**
     * find users whose name start with prefix specified without {@link Pageable}.
     *
     * @param usernamePrefix user name prefix
     * @return users whose name start with prefix specified.
     */
    @Query("select u from User u where u.userName like ?1%")
    List<User> findByUserNamePrefix(String usernamePrefix);

    /**
     * Find all users with {@link Pageable}.
     *
     * @param pageable ordering and size limitation
     * @return all users
     */
    @Query("select u from User u")
    List<User> findAllUsers(Pageable pageable);

}
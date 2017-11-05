package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.User;
import org.springframework.data.domain.Page;
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


    /**
     * Find users with role code with {@link Pageable} using native query.
     *
     * @param roleCode role code to search
     * @param pageable ordering and size limitation
     * @return list of users
     */
    @Query(nativeQuery = true,
            value = "select u.* from User u, USER_TO_USER_ROLE_MAP ur where ur.ROLE_CODE = ?1 and ur.USER_NAME = u.USER_NAME "
                    + "/* #pageable  */",
            countQuery = "select count(1) from User u, USER_TO_USER_ROLE_MAP ur where ur.ROLE_CODE = ?1 and ur.USER_NAME = u.USER_NAME")
    List<User> findUsersByRoleCode(String roleCode, Pageable pageable);

    /**
     * Find users with role code with {@link Pageable} using native query.
     *
     * @param roleCode role code to search
     * @param pageable ordering and size limitation
     * @return page of users
     */
    @Query(nativeQuery = true,
            value = "select u.* from User u, USER_TO_USER_ROLE_MAP ur where ur.ROLE_CODE = ?1 and ur.USER_NAME = u.USER_NAME "
                    + "/* #pageable  */",
            countQuery = "select count(1) from User u, USER_TO_USER_ROLE_MAP ur where ur.ROLE_CODE = ?1 and ur.USER_NAME = u.USER_NAME")
    Page<User> findUsersByRoleCode2(String roleCode, Pageable pageable);

}
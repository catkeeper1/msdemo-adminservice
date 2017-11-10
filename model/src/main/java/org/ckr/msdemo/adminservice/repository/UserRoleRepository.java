package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    /**
     * find role with role code.
     *
     * @param roleCode role code
     * @return role with role code
     */
    UserRole findByRoleCode(String roleCode);

    /**
     * find role with role code and last modified time.
     *
     * @param roleCode              rold code
     * @param lastModifiedTimestamp last modified time
     * @return role with role code and last modified time
     */
    UserRole findByRoleCodeAndUpdatedAtGreaterThanEqual(String roleCode, Timestamp lastModifiedTimestamp);


}
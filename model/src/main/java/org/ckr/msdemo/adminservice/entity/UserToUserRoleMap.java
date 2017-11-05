package org.ckr.msdemo.adminservice.entity;

import org.ckr.msdemo.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/11/5.
 */
@Entity()
@Table(name = "USER_TO_USER_ROLE_MAP",
        indexes = {@Index(name = "USER_TO_USER_ROLE_INDEX_1", columnList = "USER_NAME ASC", unique = false),
                   @Index(name = "USER_TO_USER_ROLE_INDEX_2", columnList = "ROLE_CODE", unique = false)})
public class UserToUserRoleMap extends BaseEntity {

    private UserToUserRoleMapKey primaryKey;

    private User user;

    private UserRole userRole;

    @EmbeddedId
    public UserToUserRoleMapKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(UserToUserRoleMapKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    @ManyToOne
    @JoinColumn(name = "USER_NAME", insertable = false, updatable = false)
    public User getUser() {
        return user;
    }



    @ManyToOne
    @JoinColumn(name = "ROLE_CODE", insertable = false, updatable = false)
    public UserRole getUserRole() {
        return userRole;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Embeddable
    public static class UserToUserRoleMapKey implements Serializable {

        private String userName;

        private String roleCode;


        @Column(name = "USER_NAME", unique = true, nullable = false, length = 100)
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        @Column(name = "ROLE_CODE", unique = true, nullable = false, length = 100)
        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserToUserRoleMapKey that = (UserToUserRoleMapKey) o;
            return Objects.equals(userName, that.userName) &&
                    Objects.equals(roleCode, that.roleCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userName, roleCode);
        }
    }
}

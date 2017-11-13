package org.ckr.msdemo.adminservice.entity;

import com.google.common.base.MoreObjects;
import org.ckr.msdemo.entity.BaseEntity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Store the principal that is used to login the system.
 * Every user should only has one record in this table.
 */
@Entity()
@Table(name = "USER",
       indexes = {@Index(name = "user_index_1", columnList = "USER_DESCRIPTION ASC ,IS_LOCKED DESC", unique = true),
                  @Index(name = "user_index_2", columnList = "IS_LOCKED", unique = false)})
public class User extends BaseEntity {

    private static final long serialVersionUID = 7028458717583173058L;
    private String userName;
    private String userDescription;
    private String password;
    private Boolean locked;
    private String groupCode;
    private List<UserRole> roles;
    private UserGroup group;


    public User() {
        super();
    }


    /**
     * Constract user with user name and description.
     *
     * @param userName
     * @param userDescription
     */
    public User(String userName, String userDescription) {
        super();
        this.userName = userName;
        this.userDescription = userDescription;
    }

    /**
     * The ID of a user that is used for authentication. It should be
     * equal to the user ID that is stored in the LDAP server.
     */
    @Id
    @Column(name = "USER_NAME", unique = true, nullable = false, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The password for authentication. It is used for development only because passwords
     * should be stored in LDAP server in production env.
     *
     */
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The real user full name. Usually, it is used to be displayed in screens or reports.
     *
     */
    @Column(name = "USER_DESCRIPTION", length = 200)
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    /**
     * Indicate whether an user account is locked.
     * If an user account is locked, that user account cannot be used for authentication.
     * @return return true if user is locked. Otherwise, return false.
     */
    @Column(name = "IS_LOCKED")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * The user roles that have been assigned to current users.
     *
     */
    @ManyToMany()
    @JoinTable(name = "USER_TO_USER_ROLE_MAP",
        joinColumns = {@JoinColumn(name = "USER_NAME", updatable = false, insertable = false)},
        inverseJoinColumns = {@JoinColumn(name = "ROLE_CODE", updatable = false, insertable = false)})
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * User groups for current users.
     */
    @Column(name = "GROUP_CODE", length = 100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * The group code of user group that current user belong to. This is an FK that link
     * to user group table.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_CODE", insertable = false, updatable = false)
    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userName", userName)
                .add("userDescription", userDescription)
                .add("password", "MASKED")
                .add("locked", locked)
                .add("groupCode", groupCode)
                .toString();
    }
}

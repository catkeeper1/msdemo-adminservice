package org.ckr.msdemo.adminservice.entity;

import com.google.common.base.MoreObjects;
import org.ckr.msdemo.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * This table store user info.
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
    private List<Role> roles;



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
     * The unique ID of a user.
     */
    @Id
    @Column(name = "USER_NAME", unique = true, nullable = false, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "USER_DESCRIPTION", length = 200)
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    @Column(name = "IS_LOCKED")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @ManyToMany()
    @JoinTable(name = "USER_ROLE",
        joinColumns = {@JoinColumn(name = "USER_NAME")},
        inverseJoinColumns = {@JoinColumn(name = "ROLE_CODE")})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userName", userName)
                .add("userDescription", userDescription)
                .add("password", "MASKED")
                .add("locked", locked)
                .add("roles", roles)
                .toString()
               + super.toString();
    }
}

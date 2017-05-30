package org.ckr.msdemo.adminservice.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity()
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 7028458717583173058L;

    @Id
    @Column(name = "USER_NAME", unique = true, nullable = false, length = 100)
    private String userName;

    @Column(name = "USER_DESCRIPTION")
    private String userDescription;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_LOCKED")
    private String isLocked;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_NAME")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_CODE")})
    private List<Role> roles;

    @Version
    @Column(name = "LAST_MODIFIED_TIMESTAMP")
    private Timestamp lastModifiedTimestamp;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getLocked() {
        return isLocked;
    }

    public void setLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public Timestamp getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(Timestamp lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

}

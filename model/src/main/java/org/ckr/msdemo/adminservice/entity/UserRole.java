package org.ckr.msdemo.adminservice.entity;

import com.google.common.base.MoreObjects;
import org.ckr.msdemo.entity.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity()
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 4939126105741432131L;

    private String roleCode;

    private String parentRoleCode;

    private String roleDescription;



    @Id
    @Column(name = "ROLE_CODE", unique = true, nullable = false, length = 100)
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }


    @Column(name = "PARENT_ROLE_CODE")
    public String getParentRoleCode() {
        return parentRoleCode;
    }

    public void setParentRoleCode(String parentRoleCode) {
        this.parentRoleCode = parentRoleCode;
    }

    @Column(name = "ROLE_DESCRIPTION")
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleCode", roleCode)
                .add("parentRoleCode", parentRoleCode)
                .add("roleDescription", roleDescription)
                .toString()
               + super.toString();
    }
}

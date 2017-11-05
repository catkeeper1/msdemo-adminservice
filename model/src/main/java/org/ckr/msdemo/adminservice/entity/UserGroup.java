package org.ckr.msdemo.adminservice.entity;

import com.google.common.base.MoreObjects;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/11/4.
 */
@Entity()
@Table(name = "USER_GROUP")
public class UserGroup {

    private String groupCode;

    private String groupDescription;

    private String parentGroupCode;

    private UserGroup parent;

    private List<User> userList;

    @Id
    @Column(name = "GROUP_CODE", unique = true, nullable = false, length = 100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name = "GROUP_DESCRIPTION", length = 200)
    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    @Column(name = "PARENT_GROUP_CODE", length = 100)
    public String getParentGroupCode() {
        return parentGroupCode;
    }

    public void setParentGroupCode(String parentGroupCode) {
        this.parentGroupCode = parentGroupCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_GROUP_CODE", insertable = false, updatable = false)
    public UserGroup getParent() {
        return parent;
    }

    public void setParent(UserGroup parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "group")
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("groupCode", groupCode)
                .add("groupDescription", groupDescription)
                .add("parentGroupCode", parentGroupCode)
                .toString();
    }
}

package org.ckr.msdemo.adminservice.valueobject;

import java.io.Serializable;

public class UserQueryView implements Serializable {


    private static final long serialVersionUID = -1252635759961669740L;

    private String userName;
    private String userDescription;
    private String locked;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }


}

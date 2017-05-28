package org.ckr.msdemo.adminservice.valueobject;

import java.util.List;

public class UserServiceForm {
	private String userName;
	private String userDescription;
	private String password;
	private String locked;
	
	private List<RoleServiceForm> roles;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	
	
	public List<RoleServiceForm> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleServiceForm> roles) {
		this.roles = roles;
	}


	public static class RoleServiceForm {
		private String roleCode;

		public String getRoleCode() {
			return roleCode;
		}

		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
		
	}
	

}

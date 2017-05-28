package org.ckr.msdemo.adminservice.valueobject;

public class UserDetailView extends UserServiceForm{
	
	public static class RoleDetailView extends RoleServiceForm{
		private String roleDescription;

		public String getRoleDescription() {
			return roleDescription;
		}

		public void setRoleDescription(String roleDescription) {
			this.roleDescription = roleDescription;
		} 
		
	}
}




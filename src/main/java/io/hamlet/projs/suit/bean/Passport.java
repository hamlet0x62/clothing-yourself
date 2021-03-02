package io.hamlet.projs.suit.bean;

import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.User;


public class Passport {
	
	/**
	 * 
	 */
	
	private long userId;
	private int roleId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public Role getRole() {
		return Role.getRole(roleId);
	}
	
	public static Passport genPassport(User user) {
		Passport passport = new Passport();
		passport.setRoleId(user.getRoleId());
		passport.setUserId(user.getId());
		
		
		return passport;
	}
	

}

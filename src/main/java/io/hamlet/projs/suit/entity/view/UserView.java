package io.hamlet.projs.suit.entity.view;

import io.hamlet.projs.suit.entity.IdEntity;
import io.hamlet.projs.suit.entity.Role;

import static io.hamlet.projs.suit.constant.Constant.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = "V_User")
@Entity
public class UserView extends IdEntity {
	
	private String username;
	
	private char gender;
	private int roleId;
	private String realName;
	private Long modelId;
	private String avatarFilename;
	private String bodyFilename;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public String getAvatarFilename() {
		return avatarFilename;
	}
	public void setAvatarFilename(String avatarFilename) {
		this.avatarFilename = avatarFilename;
	}
	public String getBodyFilename() {
		return bodyFilename;
	}
	public void setBodyFilename(String bodyFilename) {
		this.bodyFilename = bodyFilename;
	}
	
	public boolean getIsAdmin() {
		 return Role.getRole(roleId).equals(Role.ADMIN);
	}

	

}

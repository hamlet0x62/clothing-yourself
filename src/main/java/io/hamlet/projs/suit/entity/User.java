package io.hamlet.projs.suit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static io.hamlet.projs.suit.constant.Constant.*;


@Table(name="T_User")
@Entity
@NamedQueries(value = { 
		@NamedQuery(name = "findAll", query = "FROM User"),
		@NamedQuery(name = "findByUsername", query = "FROM User user WHERE user.username=:username"),
		@NamedQuery(name = "findOne", query = "FROM User user WHERE user.id=:id "), 
		})
@JsonIgnoreProperties(value= {"password"})
public class User extends IdEntity {	
	
	private static final long serialVersionUID = 1146252878207342123L;
	
	@Column(length=NAME_TEXT_LEN)
	private String username;
	
	@Column(length=PASSWORD_LEN)
	private String password;
	@Column
	private char gender;
	@Column
	private int roleId;
	@Column(columnDefinition = "nvarchar(25)")
	private String realName;
	@Column
	private Long modelId;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public Role getRole() {
		
		return Role.getRole(this.getRoleId());
	}
	
	public void setRole(Role role) {
		this.roleId = role.getIndex();
	}
	
	
}

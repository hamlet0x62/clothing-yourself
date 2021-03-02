package io.hamlet.projs.suit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="T_Model")
@Entity
public class Model extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1706404777355871232L;
	
	
	@Column
	private char gender;
	@Column
	private String avatarFilename;
	@Column
	private String bodyFilename;
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
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

}

package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.entity.Garment;

import javax.persistence.TypedQuery;
import javax.ws.rs.FormParam;

public class GarmentQueryForm {
	
	

	@FormParam("gender")
	private String gender;
	
	@FormParam("clazzId")
	private long clazzId = 0l;
	

	public char getGender() {
		return gender.charAt(0);
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getClazzId() {
		return clazzId;
	}

	public void setClazzId(Long clazzId) {
		this.clazzId = clazzId;
	}
	
	
	public boolean  isEmpty() {
		return this.clazzId == 0 && (gender == null || gender.length() == 0);
	}
	
	public boolean isSingleParam() {
		return this.clazzId == 0 || gender == null;
	}

	
	
	
}

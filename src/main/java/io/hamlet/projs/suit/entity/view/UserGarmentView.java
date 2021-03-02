package io.hamlet.projs.suit.entity.view;

import io.hamlet.projs.suit.entity.IdEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = "V_UserGarment")
@Entity
public class UserGarmentView extends IdEntity {
	
	private Long userId;
	private Long garmentId;
	private int zindex;
	private String displayText;
	private String no;
	private Long clazzId;
	private char gender;
	private double price;
	
	private String assetFilename;
	
	public String getAssetFilename() {
		return assetFilename;
	}
	public void setAssetFilename(String assetFilename) {
		this.assetFilename = assetFilename;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getGarmentId() {
		return garmentId;
	}
	public void setGarmentId(Long garmentId) {
		this.garmentId = garmentId;
	}
	public int getZindex() {
		return zindex;
	}
	public void setZindex(int zindex) {
		this.zindex = zindex;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Long getClazzId() {
		return clazzId;
	}
	public void setClazzId(Long clazzId) {
		this.clazzId = clazzId;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	

}

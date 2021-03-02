package io.hamlet.projs.suit.entity;


import io.hamlet.projs.suit.annotation.CheckSame;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static io.hamlet.projs.suit.constant.Constant.*;

@Table(name="T_Garment")
@Entity
public class Garment extends IdEntity {
	@CheckSame
	@Column(length=NAME_TEXT_LEN, columnDefinition = "nvarchar(50)")
	private String displayText;

	@CheckSame
	@Column(length=NAME_TEXT_LEN)
	private String no;

	@CheckSame
	@Column
	private Long clazzId;

	@CheckSame
	@Column
	private char gender;

	@CheckSame
	@Column
	private double price;

	@CheckSame
	@Column(length=FILENAME_LEN)
	private String assetFilename;
	
	
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
	public String getAssetFilename() {
		return assetFilename;
	}
	public void setAssetFilename(String assetFilename) {
		this.assetFilename = assetFilename;
	}
	
}

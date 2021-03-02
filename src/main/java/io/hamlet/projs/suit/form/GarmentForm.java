package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.annotation.CheckSame;
import io.hamlet.projs.suit.annotation.Validator;
import io.hamlet.projs.suit.entity.Garment;

import javax.ws.rs.FormParam;

import static io.hamlet.projs.suit.constant.Constant.*;

public class GarmentForm extends ValidateForm {
	
	
	@FormParam("clazzId")
	private long clazzId;
	
	@FormParam("price")
	private double price;
	
	@Validator(fixedLength=1)
	@FormParam("gender")
	private String gender;
	
	
	@Validator(minLength = 1, maxLength=NAME_TEXT_LEN)
	@FormParam("no")
	private String no;
	
	
	@Validator(minLength = 1, maxLength=NAME_TEXT_LEN)
	@FormParam("displayText")
	private String displayText;
	
	@FormParam("assetFilename")
	private String garmentFilename = "";


	public long getClazzId() {
		return clazzId;
	}


	public void setClazzId(long clazzId) {
		this.clazzId = clazzId;
	}


	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public String getDisplayText() {
		return displayText;
	}


	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public Garment fillGarment(Garment garment){
		garment.setAssetFilename(garmentFilename);
		garment.setClazzId(clazzId);
		garment.setDisplayText(displayText);
		garment.setGender(gender.charAt(0));
		garment.setNo(no);
		garment.setPrice(price);
		return garment;
	}

	public Garment getGarment() {
		return fillGarment(new Garment());
	}
	
	
	
	
	
	
	

}

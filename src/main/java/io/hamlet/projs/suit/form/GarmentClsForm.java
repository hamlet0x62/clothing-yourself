package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.annotation.Validator;
import io.hamlet.projs.suit.entity.GarmentClazz;

import javax.ws.rs.FormParam;

import static io.hamlet.projs.suit.constant.Constant.*;

public class GarmentClsForm extends ValidateForm {
	
	private GarmentClazz garmentCls;
	
	@Validator(minLength = 1, maxLength=TEXT_LEN_LONG)
	@FormParam("no")
	private String no;
	
	
	@Validator(minLength = 1, maxLength=TEXT_LEN_SHORT)
	@FormParam("clazzName")
	private String clazzName;
	
	public GarmentClazz getGarmentCls() {
		
		return fillGarmentCls(new GarmentClazz());
	}
	
	public GarmentClazz fillGarmentCls(GarmentClazz garmentCls) {
		garmentCls.setClazzName(clazzName);
		garmentCls.setNo(no);
		
		return garmentCls;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getClazzName() {
		return clazzName;
	}

}

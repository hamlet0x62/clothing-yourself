package io.hamlet.projs.suit.form;

import javax.ws.rs.FormParam;


public class AddSuitForm extends ValidateForm{
	
	@FormParam("zindex")
	private int zindex;

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}
	
	
	
	

}

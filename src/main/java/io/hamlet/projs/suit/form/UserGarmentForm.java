package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.entity.UserGarment;

import javax.ws.rs.FormParam;

public class UserGarmentForm extends ValidateForm{
	
	@FormParam("zindex")
	private int zindex;
	
	
	public UserGarment adjustSuit(UserGarment suit) {
		suit.setZindex(zindex);
		
		return suit;
	}


	public int getZindex() {
		return zindex;
	}


	public void setZindex(int zindex) {
		this.zindex = zindex;
	}
	
	
}

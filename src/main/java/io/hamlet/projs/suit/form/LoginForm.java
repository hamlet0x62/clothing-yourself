package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.annotation.Validator;

import javax.ws.rs.FormParam;

import static io.hamlet.projs.suit.constant.Constant.*;


public class LoginForm extends ValidateForm {

	@FormParam("username")
	@Validator(maxLength = NAME_TEXT_LEN, minLength = 1)
	private String username;
	
	@FormParam("password")
	@Validator(maxLength = PASSWORD_LEN, minLength = 1)
	private String password;

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
	

}

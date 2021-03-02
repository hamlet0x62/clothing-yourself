package io.hamlet.projs.suit.form;


import io.hamlet.projs.suit.annotation.Validator;
import io.hamlet.projs.suit.constant.Constant;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.User;

import javax.ws.rs.FormParam;
import java.util.Map;

public class UserRegisterForm extends ValidateForm {
	
	private User user;
	
	@FormParam("username")
	@Validator(notNull = true, minLength = 1, maxLength = Constant.NAME_TEXT_LEN)
	private String username;
	@FormParam("password")
	@Validator(notNull = true, minLength = 1, maxLength = Constant.PASSWORD_LEN)
	private String password;
	@FormParam("gender")
	@Validator(notNull = true, fixedLength = 1)
	private String gender;
	
	@FormParam("realName")
	@Validator(notNull = true, maxLength = Constant.TEXT_LEN_SHORT)
	private String realName;
	
	@FormParam("repeatpwd")
	@Validator(minLength = 1, maxLength = Constant.PASSWORD_LEN)
	private String repeatPassword;
	
	@FormParam("modelId")
	private long modelId;
	
	

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	
	
	
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public User getUser() {
		user = new User();
		user.setUsername(username);
		user.setGender(gender.charAt(0));
		user.setModelId(modelId);
		user.setRealName(realName);
		user.setRoleId(Role.USER.getIndex());
		user.setPassword(password);
		return user;
	}
	
	@Override
	public Map<String, String> getValidationRst() {
		// TODO Auto-generated method stub
		 Map<String, String> rst = super.getValidationRst();
		 if(!getPassword().equals(getRepeatPassword())) {
			 rst.put("repeatpwd", "与密码不匹配");
		 }
		 return rst;
	}
	



}

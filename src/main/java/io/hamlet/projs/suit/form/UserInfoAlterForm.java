package io.hamlet.projs.suit.form;


import io.hamlet.projs.suit.entity.User;

import javax.ws.rs.FormParam;
import java.util.Map;

public class UserInfoAlterForm extends UserUpdateForm {
	
	@FormParam("id")
	private long id;
	
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		if(password != null && password.length() > 0) {
			boolean pass =  password.equals(repeatPassword);
			if(!pass) {
				Map<String, String > validationRst = getValidationRst();
				validationRst.put("password", "重复输入的密码不一致");
				return false;
			}
		}
		return super.isValid();
	}
	
	public User fillUser(User user) {
		if(password != null && password.length() > 0) {
			user.setPassword(password);
		}
		
		user.setUsername(username);
		user.setGender(gender.charAt(0));
		user.setModelId(modelId);
		user.setRealName(realName);
		
		return user;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}

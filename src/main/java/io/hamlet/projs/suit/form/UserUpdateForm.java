package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.annotation.Validator;
import io.hamlet.projs.suit.constant.Constant;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.User;

import javax.ws.rs.FormParam;
import java.util.Map;

public class UserUpdateForm extends ValidateForm{
	
	@FormParam("username")
	@Validator(notNull = true, minLength = 1, maxLength = Constant.NAME_TEXT_LEN)
	protected String username;
	
	@FormParam("password")
	protected String password;
	
	@FormParam("gender")
	@Validator(notNull = true, fixedLength = 1)
	protected String gender;
	
	@FormParam("realName")
	@Validator(notNull = true, maxLength = Constant.TEXT_LEN_SHORT)
	protected String realName;
	
	@FormParam("repeatpwd")
	protected String repeatPassword;
	
	@FormParam("modelId")
	protected long modelId;
	
	@FormParam("isAdmin")
	protected Integer isAdmin;
	
	public boolean isSame(User user) {
		
		if(isPasswordModified()) {
			return false;
		}else if(isRoleModified(user)){
			return false;
		}else {
			return user.getRealName().equals(realName)
					&& user.getModelId().equals(modelId)
					&& user.getGender() == gender.charAt(0)
					&& user.getModelId() == modelId;
		}
	}
	
	private boolean isRoleModified(User user) {
		// TODO Auto-generated method stub
		return isAdmin != null && !user.getRole().equals(getRole());
	}

	public Role getRole() {
		return isAdmin == 1 ? Role.ADMIN: Role.USER;
	}
	
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		if(password != null && password.length() > 0) {
			boolean rst =  password.equals(repeatPassword);
			if(rst == false) {
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
		if(isRoleModified(user)) {
			user.setRoleId(getRole().getIndex());
		}
		user.setUsername(username);
		user.setGender(gender.charAt(0));
		user.setModelId(modelId);
		user.setRealName(realName);
		
		
		return user;
		
	}
	
	public boolean isPasswordModified() {
		return password != null && password.length() > 0;
	}
}

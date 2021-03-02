package io.hamlet.projs.suit.api;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import io.hamlet.projs.suit.entity.User;
import io.hamlet.projs.suit.repository.UserRepository;
import io.hamlet.projs.suit.repository.UserViewRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import io.hamlet.projs.suit.annotation.RoleRequire;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.constant.URLS;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.User;
import io.hamlet.projs.suit.entity.view.UserView;
import io.hamlet.projs.suit.form.UserUpdateForm;
import io.hamlet.projs.suit.form.UserInfoAlterForm;
import io.hamlet.projs.suit.form.UserRegisterForm;
import io.hamlet.projs.suit.utils.PassportUtil;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.stereotype.Component;

@Component
@Path("/user/")
@RoleRequire(Role.ADMIN)
public class UserApi extends ApiMixin<User, UserRepository> {
	
	@Autowired
	UserRepository userRepo;
	
	@Context
	private HttpServletRequest request;
	
	@Autowired
	UserViewRepository userViewRepo;
	
	
	@RoleRequire(Role.USER)
	@Path("/self")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public Result getSelf() {
		long userId = PassportUtil.getCurrentUserId(request);
		System.out.println("userId: " + userId);
		UserView view = userViewRepo.findById(userId).get();
		return ResultUtils.success(view);
	}
	
	@Path("/{id}")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public Result getUser(@PathParam(value = "id") Long id){
		Optional<UserView> opt =  userViewRepo.findById(id);
		if(!opt.isPresent()) {
			return ResultUtils.error("未找到该记录");
		}else {
			return ResultUtils.success(opt.get());
		}
	}
	
	@Path("/all")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public Result getUsers(){
		return ResultUtils.success("", userViewRepo.findAll());
	}
	
	@Path("/update/{id}")
	@Produces(value="application/json;charset=UTF-8")
	@POST
	public Result update(@PathParam("id")long userId, @Form UserUpdateForm form) {
		if(form.isValid()) {
			User user = findOne(userId);
			if(form.isSame(user)) {
				return ResultUtils.success("无修改");
			}else {
				user = form.fillUser(user);
				System.out.println("UserRole: " + form.getRole());
				update(user);
				PassportUtil.expires(user.getId());
				UserView view = userViewRepo.findById(user.getId()).get();
				return ResultUtils.success("修改成功", view);
			}
		}else {
			return ResultUtils.formValidationError(form.getValidationRst());
		}
	}
	
	@RoleRequire(Role.USER)
	@Path("/selfupdate")
	@Produces(value="application/json;charset=UTF-8")
	@POST
	public Result selfUpdate(@Form UserUpdateForm form){
		if(form.isValid()) {
			long userId = PassportUtil.getCurrentUserId(request);
			User user = findOne(userId);
			if(form.isSame(user)) {
				return ResultUtils.success("无修改");
			}else {
				user = form.fillUser(user);
				Result rst = update(user);
				if(form.isPasswordModified()) {
					return ResultUtils.redirect(URLS.LOGIN_URL, "修改成功，请重新登陆");
				}else {
					return rst;
				}
			}
			
		}else {
			return ResultUtils.formValidationError(form.getValidationRst());
		}
		
	}
	
	@RoleRequire(Role.ANONYMOUS)
	@Path("/create")
	@Produces(value="application/json;charset=UTF-8")
	@POST
	public Result registerUser(@Form UserRegisterForm form) {
		if(form.isValid()) {
			User user = form.getUser();
			user.setId(0);
			user.setRoleId(Role.USER.getIndex());
			User existedUser = userRepo.findByUsername(user.getUsername());
			if(existedUser == null) {
				create(user);
				return ResultUtils.redirect(URLS.LOGIN_URL, "注册成功");
			}else {
				return ResultUtils.error("用户名已被占用");
			}
		}else {
			return form.sendInvalidResult();
		}
		
	}
	
	@Path("/del/{userId}")
	@Produces("application/json;charset=UTF-8")
	@POST
	public Result removeUser(@PathParam("userId") long userId) {
		User user = findOne(userId);
		if(user == null) {
			return ResultUtils.error("未找到记录");
		}
		return remove(user);
		
	}

	@Override
	Class getEntityClass() {
		// TODO Auto-generated method stub
		return User.class;
	}

	@Override
	UserRepository getRepo() {
		return userRepo;
	}
}

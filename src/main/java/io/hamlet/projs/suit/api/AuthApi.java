package io.hamlet.projs.suit.api;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import io.hamlet.projs.suit.repository.UserRepository;
import org.jboss.resteasy.annotations.Form;

import io.hamlet.projs.suit.annotation.RoleRequire;
import io.hamlet.projs.suit.bean.Passport;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.User;
import io.hamlet.projs.suit.form.LoginForm;
import io.hamlet.projs.suit.utils.PassportUtil;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.hamlet.projs.suit.constant.URLS.*;

@Component
@Path("/auth")
public class AuthApi extends ApiMixin<User, UserRepository> {
	
	@Context
	HttpServletRequest request;

	@Autowired
	UserRepository userRepo;
	
	
	@Path("/login")
	@Produces(value="application/json;charset=UTF-8")
	@POST
	public Result login(@Form LoginForm form ) {
		System.out.println(form.getUsername());
		if (form.isValid()) {
			User user = userRepo.findByUsername(form.getUsername());

			if(user != null) {
				System.out.println(form.getPassword());
				System.out.println(user.getUsername());
				if(!user.getPassword().equals(form.getPassword())) {
					return ResultUtils.error("密码错误");
				}
				request.getSession().setAttribute("passport", Passport.genPassport(user));
				return ResultUtils.redirect(INDEX_PAGE);
			}else {
				return ResultUtils.error("用户名不存在");
			}
		}else {
			return form.sendInvalidResult();
		}

	}
	
	
	@RoleRequire(Role.ANONYMOUS)
	@Path("/roleLabel")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public Result getRoleLabel() {
		Role role = PassportUtil.getCurrentRole(request);
		return ResultUtils.success(role);
	}
	
	@RoleRequire(Role.USER)
	@Path("/logout")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public Result logout() {
		request.getSession().removeAttribute("passport");
		return ResultUtils.redirect(LOGIN_URL, "退出成功");
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

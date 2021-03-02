package io.hamlet.projs.suit.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


import com.sun.net.httpserver.Authenticator;
import io.hamlet.projs.suit.annotation.RoleRequire;
import io.hamlet.projs.suit.bean.Passport;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.constant.Constant;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.utils.PassportUtil;
import io.hamlet.projs.suit.utils.ResponseUtils;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import static io.hamlet.projs.suit.constant.URLS.*;
@Component
@Provider
public class RoleRequireInterceptor implements ContainerRequestFilter {
	public static final String RESOURCE_INVOKER_KEY = "org.jboss.resteasy.core.ResourceMethodInvoker";

	
	@Context
	HttpServletRequest request;


	private ServerResponse filter0(ResourceMethodInvoker resourceMethodInvoker) {
		ServerResponse response = null;
		Class clazz = resourceMethodInvoker.getResourceClass();
		Method method = resourceMethodInvoker.getMethod();
		System.out.println("Interceptor Entered.");

		// 先检查方法上的标注，如果方法上没有标注，则沿用方法所在类的标注
		if(method.isAnnotationPresent(RoleRequire.class)) {
			System.out.println("Method Entered.");
			RoleRequire require = (RoleRequire)method.getAnnotation(RoleRequire.class);
			response = dealWith(require);

		}else if(clazz.isAnnotationPresent(RoleRequire.class)) {
			System.out.println(" Class Entered.");
			RoleRequire require = (RoleRequire)clazz.getAnnotation(RoleRequire.class);
			response = dealWith(require);
		}

		return response;
	}


	private ServerResponse dealWith(RoleRequire require) {
		Role role = require.value();
		Passport passport = (Passport) request.getSession().getAttribute("passport");
		Role userRole = Role.ANONYMOUS;
		if(passport != null) {
			if(PassportUtil.isExpired(passport)) {
				request.getSession().removeAttribute("passport");
				PassportUtil.removeFromExpired(passport);
			}else {
				userRole = passport.getRole();
			}
		}
		// System.out.println(userRole);
		// System.out.println(role.compareTo(userRole));
		if(role.compareTo(userRole) > 0) {
			// login required
			System.out.println(userRole);

			if(Role.ANONYMOUS.equals(userRole)) {
				if(Constant.DEV) {
					passport = new Passport();
					passport.setUserId(1);
					passport.setRoleId(Role.ADMIN.getIndex());
					request.getSession().setAttribute("passport", passport);
					return null;
					
				}
				return ResponseUtils.ok(ResultUtils.redirect(LOGIN_URL, "请先登录"));
			}
			Result authFailedResult = ResultUtils.error("权限不足，只有权限大于:" + role + "才可访问." );
			return ResponseUtils.ok(authFailedResult);
		}
		return null;
	}


	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		ResourceMethodInvoker invoker = (ResourceMethodInvoker) containerRequestContext.getProperty(RESOURCE_INVOKER_KEY);
		Response resp = filter0(invoker);
		if (resp != null) {
			containerRequestContext.abortWith(resp);
		}
	}
}

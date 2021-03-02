package io.hamlet.projs.suit.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;


import io.hamlet.projs.suit.bean.Passport;
import io.hamlet.projs.suit.entity.Role;

public class PassportUtil {
	
	
	final static Set<Long> expiredSet = java.util.Collections.synchronizedSet(new HashSet<Long>());
	
	public static final Passport getCurrentPassport(HttpServletRequest request) {
		
		Passport passport = (Passport) request.getSession().getAttribute("passport");
		return passport;
	}
	
	public static final long getCurrentUserId(HttpServletRequest request) {
		
		Passport passport = (Passport) request.getSession().getAttribute("passport");
		return passport.getUserId();
		
	}
	
	public static final boolean hasPassport(HttpServletRequest request) {
		return request.getSession().getAttribute("passport") != null;
	}
	
	public static final Role getCurrentRole(HttpServletRequest request) {
		
		if(!hasPassport(request)) {
			return Role.ANONYMOUS;
		}else {
			int roleId = getCurrentPassport(request).getRoleId();
			return Role.getRole(roleId);
		}
		
	}
	
	public static final void expires(Passport passport) {
		expiredSet.add(passport.getUserId());
	}
	
	public static final boolean isExpired(Passport passport) {
		return expiredSet.contains(passport.getUserId());
	}
	
	public static final void removeFromExpired(Passport passport) {
		expiredSet.remove(passport.getUserId());
	}
	
	public static final void expires(Long userId) {
		expiredSet.add(userId);
	}
	
	
	
}

package io.hamlet.projs.suit.utils;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import io.hamlet.projs.suit.bean.Result;

public class ResponseUtils {
	
	public static ServerResponse ok(Result rst) {
		return new ServerResponse(rst, 200, new Headers<>());
	}

}

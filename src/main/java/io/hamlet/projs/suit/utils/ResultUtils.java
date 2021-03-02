package io.hamlet.projs.suit.utils;


import io.hamlet.projs.suit.bean.Result;

import static io.hamlet.projs.suit.constant.URLS.*;

public class ResultUtils {
	
	final static String EMPTY_DATA = "";
	final static String NO_ACTION = "";
	
	final static String FORM_ERROR_TEXT = "发生参数验证错误，请按照规则修正参数值";

	/*
	 * deprecated method 
	 */
	public static Result success(Object data, String msg) {
		Result rst = new Result(ResultCode.SUCCESS, msg, data,  NO_ACTION);
		return rst;
	}
	public static Result success(String msg, Object data) {
		Result rst = new Result(ResultCode.SUCCESS, msg, data,  NO_ACTION);
		return rst;
	}
	
	public static Result success(String msg) {
		return new Result(ResultCode.SUCCESS, msg, EMPTY_DATA,  NO_ACTION);
	}
	
	public static Result success(Object data) {
		Result rst = new Result(ResultCode.SUCCESS, EMPTY_DATA, data,  NO_ACTION);
		return rst;
	}
	
	public static Result error(String msg) {
		return new Result(ResultCode.ERROR, msg, EMPTY_DATA, NO_ACTION);
	}
	
	public static Result error(String msg, Object data) {
		return new Result(ResultCode.ERROR, msg, data, NO_ACTION);
	}
	
	public static Result redirect(String nextAction, String msg) {
		return new Result(ResultCode.REDIRECT, msg, EMPTY_DATA, nextAction );
		
	}
	public static Result redirect(String nextAction) {
		return redirect(nextAction, EMPTY_DATA);
	}
	
	public static Result formValidationError(Object data) {
		return error(FORM_ERROR_TEXT, data);
	}
	
}

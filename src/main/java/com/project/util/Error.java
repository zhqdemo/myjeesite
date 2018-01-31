package com.project.util;

public class Error {
	/**成功*/
	public static final int ERR_SUCCESS = 0;
	/**失败*/
	public static final int ERR_FAILED = 1;
	/**验证码错误*/
	public static final int ERR_VERIFY_CODE_WRONG = 2;
	/**验证码失败*/
	public static final int ERR_VERIFY_CODE_EXPIRED = 3;
	/**帐号已存在*/
	public static final int ERR_ACCOUNT_HAD_EXITED = 4;
	/**此手机号已被注册*/
	public static final int ERR_TEL_HAD_REGISTED = 5;
	/**密码错误*/
	public static final int ERR_PASSWORD_WRONG = 6;
	/**密码不一致*/
	public static final int ERR_PWD_NOT_SAME = 7;
	/**帐号不存在*/
	public static final int ERR_ACCOUNT_NOT_EXIST = 8;
	/**无权限*/
	public static final int ERR_NO_POWER = 9;
	/**未登录*/
	public static final int ERR_UNLOGIN = 10;	
	
	public static String getErrorString(int nError)
	{
		switch(nError)
		{
		case ERR_SUCCESS:
			return "成功";
		case ERR_FAILED:
			return "失败";
		case ERR_VERIFY_CODE_WRONG:
			return "验证码错误";
		case ERR_VERIFY_CODE_EXPIRED:
			return "验证码已过期";
		case ERR_ACCOUNT_HAD_EXITED:
			return "账号已存在";
		case ERR_TEL_HAD_REGISTED:
			return "此手机号已被注册";
		case ERR_PASSWORD_WRONG:
			return "密码错误";
		case ERR_PWD_NOT_SAME:
			return "两次输入的密码不一致";
		case ERR_ACCOUNT_NOT_EXIST:
			return "账号不存在!";
		case ERR_NO_POWER:
			return "无权限!";
		case ERR_UNLOGIN:
			return "用户未登录!";		
		}
		
		return "没有找到["+nError+"]对应的错误信息！";
	}
}

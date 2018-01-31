package com.project.util;

import java.io.Serializable;



/**
 * 通用json结果返回对象
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ResultBean implements Serializable {
	private int code = 1;
	private String msg;
	private Object data;
	private String url;//回传需要跳转的url
	public int getCode() {
		return code;
	}
	/**
	 * 设置错误代码，系统默认找到错误信息并设置到msg。若要重设msg可也再单独调用msg的set方法
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
		this.msg = Error.getErrorString(code);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ResultBean [code=" + code + ", msg=" + msg + ", data=" + data + ", url=" + url + "]";
	}
	
}

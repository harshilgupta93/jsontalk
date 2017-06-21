package org.spring.ajax.model;

import java.util.List;

import org.spring.ajax.jsonview.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {

	@JsonView(Views.Public.class)
	private String msg;
	@JsonView(Views.Public.class)
	private String code;
	@JsonView(Views.Public.class)
	private List<User> result;
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<User> getResult() {
		return result;
	}
	
	public void setResult(List<User> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "AjaxResponseBody [msg=" + msg + ", code=" + code + ", result=" + result + "]";
	}

}

package com.sim.cms.vo;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	
	private String token;

	public LoginResponse() {
	}
	
	public LoginResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	

	
}
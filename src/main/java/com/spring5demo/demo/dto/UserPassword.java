package com.spring5demo.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserPassword {
	
	@NotNull
	@Size(min = 4, max = 60)
	private String oldPassword;
	
	@NotNull
	@Size(min = 4, max = 60)
	private String password;
	
	@NotNull
	@Size(min = 4, max = 60)
	private String matchingPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	
}

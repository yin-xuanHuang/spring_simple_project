package com.spring5demo.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserPassword {
	
	@NotNull(message = "{validate.user.notnull}")
	@Size(min = 4, max = 60, message = "{validate.user.four2sixty}")
	private String oldPassword;
	
	@NotNull(message = "{validate.user.notnull}")
	@Size(min = 4, max = 60, message = "{validate.user.four2sixty}")
	private String password;
	
	@NotNull(message = "{validate.user.notnull}")
	@Size(min = 4, max = 60, message = "{validate.user.four2sixty}")
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

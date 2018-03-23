package com.spring5demo.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.spring5demo.demo.domain.Constants;

public class UserRegister {
	
	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 4, max = 50)
	private String username;

	@NotNull
	@Size(min = 4, max = 60)
	private String password;
	
	@NotNull
	@Size(min = 4, max = 60)
	private String matchingPassword;

	@NotNull
	@Email
	@Size(min = 5, max = 100)
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserRegister [username=" + username + ", password=" + password + ", matchingPassword="
				+ matchingPassword + ", email=" + email + "]";
	}
	
	
}

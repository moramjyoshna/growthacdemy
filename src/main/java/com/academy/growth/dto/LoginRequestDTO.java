package com.academy.growth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginRequestDTO {
	@Email
	private String emailId;
	@NotNull
	private String password;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

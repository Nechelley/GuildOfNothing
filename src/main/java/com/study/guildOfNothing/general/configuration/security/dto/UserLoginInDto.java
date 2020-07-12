package com.study.guildOfNothing.general.configuration.security.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserLoginInDto {

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsernamePasswordAuthenticationToken createAuthenticationToken() {
		return new UsernamePasswordAuthenticationToken(this.email, this.password);
	}

}

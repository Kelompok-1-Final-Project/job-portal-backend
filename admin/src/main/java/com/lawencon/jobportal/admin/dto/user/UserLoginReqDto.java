package com.lawencon.jobportal.admin.dto.user;

public class UserLoginReqDto {
	private String userEmail;
	private String userPassword;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserName(String userName) {
		this.userEmail = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}

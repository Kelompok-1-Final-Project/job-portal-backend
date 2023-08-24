package com.lawencon.jobportal.candidate.dto.user;

public class UserChangePassReqDto {
	private String userId;
	private String userOldPass;
	private String userNewPass;
	private String userConfirmNewPass;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserOldPass() {
		return userOldPass;
	}

	public void setUserOldPass(String userOldPass) {
		this.userOldPass = userOldPass;
	}

	public String getUserNewPass() {
		return userNewPass;
	}

	public void setUserNewPass(String userNewPass) {
		this.userNewPass = userNewPass;
	}

	public String getUserConfirmNewPass() {
		return userConfirmNewPass;
	}

	public void setUserConfirmNewPass(String userConfirmNewPass) {
		this.userConfirmNewPass = userConfirmNewPass;
	}
	
	

}

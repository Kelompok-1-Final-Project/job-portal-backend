package com.lawencon.jobportal.admin.dto.user;

public class UserUpdateIsActiveReqDto {
	private String userId;
	private Boolean isActive;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}

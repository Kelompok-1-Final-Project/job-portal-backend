package com.lawencon.jobportal.admin.dto.userskill;

public class UserSkillUpdateReqDto {
	private String userSkillId;
	private String levelCode;

	public String getUserSkillId() {
		return userSkillId;
	}

	public void setUserSkillId(String userSkillId) {
		this.userSkillId = userSkillId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

}

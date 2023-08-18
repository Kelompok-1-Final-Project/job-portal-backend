package com.lawencon.jobportal.candidate.constant;

public enum RoleEnum {
	ADMIN("Admin", "RL001"), HR("Human Resource", "RL002"), INTERVIEWER("Interviewer", "RL003");

	final public String roleName;
	final public String roleCode;

	RoleEnum(String roleName, String roleCode){
		this.roleName = roleName;
		this.roleCode = roleCode;
	}
}

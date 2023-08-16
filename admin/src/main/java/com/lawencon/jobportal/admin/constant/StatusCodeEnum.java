package com.lawencon.jobportal.admin.constant;

public enum StatusCodeEnum {
	APPLICATION("Application", "SP001"), ASSESSMENT("Assessment", "SP002"), INTERVIEW("Interview", "SP003"),
	MCU("Medical Check Up", "SP004"), OFFERING("Offering", "SP005"), HIRED("Hired", "SP006"),
	REJECTED("Rejected", "SP007"), BLACKLIST("Blacklist", "SP008");

	final public String processName;
	final public String processCode;

	StatusCodeEnum(String processName, String processCode) {
		this.processName = processName;
		this.processCode = processCode;
	}
}

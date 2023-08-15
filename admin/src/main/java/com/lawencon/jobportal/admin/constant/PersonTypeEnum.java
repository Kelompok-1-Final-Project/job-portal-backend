package com.lawencon.jobportal.admin.constant;

public enum PersonTypeEnum {
	CANDIDATE("Candidate", "PT001"), REVIEWER("Employee", "PT002");
	
	final public String typeName;
	final public String typeCode;
	
	PersonTypeEnum(String typeName, String typeCode){
		this.typeName = typeName;
		this.typeCode = typeCode;
	}
}

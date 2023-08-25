package com.lawencon.jobportal.admin.dto.medicalcheckup;

public class MedicalCheckupUpdateReqDto {
	private String medicalId;
	private String file;
	private String ext;

	public String getMedicalId() {
		return medicalId;
	}

	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}

package com.lawencon.jobportal.candidate.dto.company;

public class CompanyInsertReqDto {

	private String companyName;
	private String companyCode;
	private String industryCode;
	private String cityCode;
	private String ext;
	private String file;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}

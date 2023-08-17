package com.lawencon.jobportal.admin.dto.blacklist;

public class BlacklistGetResDto {
	private String blacklistId;
	private String candidateId;
	private String candidateName;
	private String companyId;
	private String companyName;

	public String getBlacklistId() {
		return blacklistId;
	}

	public void setBlacklistId(String blacklistId) {
		this.blacklistId = blacklistId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

package com.lawencon.jobportal.admin.dto.result;

import java.util.List;

public class ResultInsertReqDto {

	private String candidateId;
	private String skillTestId;
	private List<Boolean> answer;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getSkillTestId() {
		return skillTestId;
	}

	public void setSkillTestId(String skillTestId) {
		this.skillTestId = skillTestId;
	}

	public List<Boolean> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Boolean> answer) {
		this.answer = answer;
	}

}

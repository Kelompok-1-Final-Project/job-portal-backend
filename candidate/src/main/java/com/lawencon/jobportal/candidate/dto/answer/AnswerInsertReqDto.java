package com.lawencon.jobportal.candidate.dto.answer;

import java.util.List;

public class AnswerInsertReqDto {
	private String skillTestId;
	private String candidateId;
	private List<AnswerCandidateReqDto> answerCandidateReqDtos;

	public String getSkillTestId() {
		return skillTestId;
	}

	public void setSkillTestId(String skillTestId) {
		this.skillTestId = skillTestId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public List<AnswerCandidateReqDto> getAnswerCandidateReqDtos() {
		return answerCandidateReqDtos;
	}

	public void setAnswerCandidateReqDtos(List<AnswerCandidateReqDto> answerCandidateReqDtos) {
		this.answerCandidateReqDtos = answerCandidateReqDtos;
	}

}

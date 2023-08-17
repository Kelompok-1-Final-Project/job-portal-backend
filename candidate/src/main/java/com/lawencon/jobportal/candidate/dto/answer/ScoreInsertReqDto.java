package com.lawencon.jobportal.candidate.dto.answer;

public class ScoreInsertReqDto {
	private Double score;
	private String notes;
	private String candidateEmail;
	private String skillTestCode;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getSkillTestCode() {
		return skillTestCode;
	}

	public void setSkillTestCode(String skillTestCode) {
		this.skillTestCode = skillTestCode;
	}

}

package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.SkillTestDao;
import com.lawencon.jobportal.admin.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestGetResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestQuestionInsertReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.Question;
import com.lawencon.jobportal.admin.model.SkillTest;
import com.lawencon.jobportal.admin.model.SkillTestQuestion;

public class SkillTestService {

	@Autowired
	private SkillTestDao skillTestDao;

	@Autowired
	private SkillTestQuestionDao skillTestQuestionDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private CandidateDao candidateDao;

	public List<SkillTestGetResDto> getAll() {
		final List<SkillTestGetResDto> skillTestGetResDtos = new ArrayList<>();

		skillTestDao.getAll(SkillTest.class).forEach(st -> {
			final SkillTestGetResDto skillTestGetResDto = new SkillTestGetResDto();
			skillTestGetResDto.setId(st.getId());
			skillTestGetResDto.setTestName(st.getTestName());
			skillTestGetResDto.setJobName(st.getJob().getJobTitle());
			skillTestGetResDto.setGrade(st.getGrade());
			skillTestGetResDto.setNotes(st.getNotes());
			skillTestGetResDto.setCandidateName(st.getCandidate().getCandidateProfile().getFullName());
			skillTestGetResDto.setVer(st.getVersion());
			skillTestGetResDtos.add(skillTestGetResDto);
		});

		return skillTestGetResDtos;
	}

	public SkillTestGetResDto getByCandidateAndJob(String candidateId, String jobId) {
		final SkillTest skillTest = skillTestDao.getByCandidateAndJob(candidateId, jobId);

		final SkillTestGetResDto skillTestGetResDto = new SkillTestGetResDto();
		skillTestGetResDto.setId(skillTest.getId());
		skillTestGetResDto.setTestName(skillTest.getTestName());

		final Job job = jobDao.getById(Job.class, jobId);
		skillTestGetResDto.setJobName(job.getJobTitle());

		skillTestGetResDto.setGrade(skillTest.getGrade());
		skillTestGetResDto.setNotes(skillTest.getNotes());

		final Candidate candidate = candidateDao.getById(Candidate.class, candidateId);
		skillTestGetResDto.setCandidateName(candidate.getCandidateProfile().getFullName());

		skillTestGetResDto.setVer(skillTest.getVersion());

		return skillTestGetResDto;
	}

	public InsertResDto assignTest(List<SkillTestQuestionInsertReqDto> data) {
		final InsertResDto insertResDto = new InsertResDto();

		if (data.size() != 0 && data != null) {
			data.forEach(d -> {
				final SkillTestQuestion skillTestQuestion = new SkillTestQuestion();

				final Question question = new Question();
				question.setId(d.getQuestionId());
				skillTestQuestion.setQuestion(question);

				final SkillTest skillTest = new SkillTest();
				skillTest.setId(d.getSkillTestId());
				skillTestQuestion.setSkillTest(skillTest);

				skillTestQuestionDao.save(skillTestQuestion);
			});
			insertResDto.setMessage("Assign Test Success");
		}
		else {
			insertResDto.setMessage("Assign Test Failed");
		}
		return insertResDto;
	}
}

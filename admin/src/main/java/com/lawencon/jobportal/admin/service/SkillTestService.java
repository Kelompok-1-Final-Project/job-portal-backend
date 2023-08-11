package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.SkillTestDao;
import com.lawencon.jobportal.admin.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestGetResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestInsertReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestQuestionInsertReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestUpdateReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.Question;
import com.lawencon.jobportal.admin.model.SkillTest;
import com.lawencon.jobportal.admin.model.SkillTestQuestion;

@Service
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
	
	@Transactional
	public UpdateResDto update(SkillTestUpdateReqDto data) {
		ConnHandler.begin();
		final UpdateResDto updateResDto = new UpdateResDto();
		
		final SkillTest skillTest = skillTestDao.getById(SkillTest.class, data.getId());
		skillTest.setGrade(data.getGrade());
		skillTest.setNotes(data.getNotes());
		
		final SkillTest result = skillTestDao.saveAndFlush(skillTest);
		
		updateResDto.setMessage("Score Inputted");
		updateResDto.setVersion(result.getVersion());		
		
		ConnHandler.commit();		
		return updateResDto;
	}
  
	public InsertResDto insertSkillTest(SkillTestInsertReqDto data) {
		final SkillTest skillTest = new SkillTest();
		final Job job = jobDao.getById(Job.class, data.getJobId());
		skillTest.setTestName(data.getTestName());
		skillTest.setJob(job);
		final SkillTest skillTestResult = skillTestDao.save(skillTest);
		final InsertResDto result = new InsertResDto();
		result.setId(skillTestResult.getId());
		result.setMessage("Insert Skill Test Successfully.");
		return result;
	}
	
	@Override
	public CountScoreResDto countScore(CountScoreReqDto countScoreReqDto) {
		final CountScoreResDto countScoreResDto = new CountScoreResDto();
		if (countScoreReqDto != null) {
			final double grade = (countScoreReqDto.getTotalTrue() / countScoreReqDto.getTotalMultiChoice()) * 100.0;
			countScoreResDto.setGrade(grade);
			countScoreResDto.setNotes("Kamu benar " + countScoreReqDto.getTotalTrue() + " dari total "
					+ countScoreReqDto.getTotalMultiChoice() + " pertanyaan pilihan ganda");
		}
		return countScoreResDto;
	}
}

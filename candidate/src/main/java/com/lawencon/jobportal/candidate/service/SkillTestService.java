package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.JobDao;
import com.lawencon.jobportal.candidate.dao.QuestionDao;
import com.lawencon.jobportal.candidate.dao.SkillTestDao;
import com.lawencon.jobportal.candidate.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.skilltest.SkillTestGetResDto;
import com.lawencon.jobportal.candidate.dto.skilltest.SkillTestInsertReqDto;
import com.lawencon.jobportal.candidate.dto.skilltest.SkillTestQuestionInsertReqDto;
import com.lawencon.jobportal.candidate.dto.skilltest.SkillTestUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.Question;
import com.lawencon.jobportal.candidate.model.SkillTest;
import com.lawencon.jobportal.candidate.model.SkillTestQuestion;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class SkillTestService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private SkillTestDao skillTestDao;

	@Autowired
	private SkillTestQuestionDao skillTestQuestionDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private UserDao candidateDao;
	
	@Autowired
	private QuestionDao questionDao;

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
		final SkillTest skillTest = skillTestDao.getByJob(jobId);

		final SkillTestGetResDto skillTestGetResDto = new SkillTestGetResDto();
		skillTestGetResDto.setId(skillTest.getId());
		skillTestGetResDto.setTestName(skillTest.getTestName());

		final Job job = jobDao.getById(Job.class, jobId);
		skillTestGetResDto.setJobName(job.getJobTitle());

		final User candidate = candidateDao.getById(User.class, candidateId);
		skillTestGetResDto.setCandidateName(candidate.getProfile().getFullName());

		skillTestGetResDto.setVer(skillTest.getVersion());

		return skillTestGetResDto;
	}

	public InsertResDto assignTest(SkillTestQuestionInsertReqDto data) {
		em().getTransaction().begin();

		final InsertResDto insertResDto = new InsertResDto();

		try {
			if (data != null) {
				SkillTestQuestion skillTestQuestion = null;
				for (String code : data.getQuestionCode()) {
					skillTestQuestion = new SkillTestQuestion();
					
					final Question question = questionDao.getByCode(code);
					skillTestQuestion.setQuestion(question);
					
					SkillTest skillTest = skillTestDao.getByCode(data.getSkillTestCode());
					skillTestQuestion.setSkillTest(skillTest);

					skillTestQuestionDao.save(skillTestQuestion);
				}
				
				insertResDto.setMessage("Assign Test Success");
				em().getTransaction().commit();
			} else {
				insertResDto.setMessage("Assign Test Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}

		return insertResDto;
	}

	public UpdateResDto update(SkillTestUpdateReqDto data) {
		em().getTransaction().begin();
		final UpdateResDto updateResDto = new UpdateResDto();

		final SkillTest skillTest = skillTestDao.getById(SkillTest.class, data.getId());

		final SkillTest result = skillTestDao.saveAndFlush(skillTest);

		updateResDto.setMessage("Score Inputted");
		updateResDto.setVersion(result.getVersion());

		em().getTransaction().commit();
		return updateResDto;
	}

	public InsertResDto insertSkillTest(SkillTestInsertReqDto data) {
		em().getTransaction().begin();

		final SkillTest skillTest = new SkillTest();
		final Job job = jobDao.getByCode(data.getJobCode());
		skillTest.setTestName(data.getTestName());
		skillTest.setTestCode(data.getTestCode());
		skillTest.setJob(job);
		final SkillTest skillTestResult = skillTestDao.save(skillTest);

		final InsertResDto result = new InsertResDto();
		result.setId(skillTestResult.getId());
		result.setMessage("Insert Skill Test Successfully.");

		em().getTransaction().commit();
		return result;
	}

}

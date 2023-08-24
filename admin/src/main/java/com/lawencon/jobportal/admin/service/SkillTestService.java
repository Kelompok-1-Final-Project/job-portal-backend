package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.QuestionDao;
import com.lawencon.jobportal.admin.dao.QuestionOptionDao;
import com.lawencon.jobportal.admin.dao.SkillTestDao;
import com.lawencon.jobportal.admin.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.skilltest.QuestionGetResDto;
import com.lawencon.jobportal.admin.dto.skilltest.QuestionOptionGetResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestGetResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestInsertReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestQuestionInsertReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestUpdateReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.TestGetResDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.Question;
import com.lawencon.jobportal.admin.model.SkillTest;
import com.lawencon.jobportal.admin.model.SkillTestQuestion;
import com.lawencon.jobportal.admin.util.GeneratorId;

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
	private CandidateDao candidateDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionOptionDao questionOptionDao;
	
	@Autowired
	private RestTemplate restTemplate;

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

	public TestGetResDto getAllQuestion(String jobId){
		final TestGetResDto testGetResDto = new TestGetResDto();
		
		final SkillTest skillTest = skillTestDao.getByJob(jobId);
		testGetResDto.setTestId(skillTest.getId());
		testGetResDto.setTestName(skillTest.getTestName());
		testGetResDto.setTestCode(skillTest.getTestCode());
		final List<QuestionGetResDto> questionGetResDtos = new ArrayList<>();
		
		skillTestQuestionDao.getBySkillTest(skillTest.getId()).forEach(stq -> {
			final QuestionGetResDto questionGetResDto = new QuestionGetResDto();
			final List<QuestionOptionGetResDto> questionOptionGetResDtos = new ArrayList<>();
			
			questionOptionDao.getByQuestion(stq.getQuestion().getId()).forEach(qo -> {
				final QuestionOptionGetResDto questionOptionGetResDto = new QuestionOptionGetResDto();
				questionOptionGetResDto.setOptionId(qo.getId());
				questionOptionGetResDto.setLabel(qo.getLabels());
				questionOptionGetResDto.setIsAnswer(qo.getIsAnswer());
				questionOptionGetResDtos.add(questionOptionGetResDto);
			});
			
			questionGetResDto.setQuestion(stq.getQuestion().getQuestion());
			questionGetResDto.setQuestionId(stq.getQuestion().getId());
			questionGetResDto.setOptionGetResDtos(questionOptionGetResDtos);
			questionGetResDtos.add(questionGetResDto);
		});
		
		testGetResDto.setQuestionGetResDtos(questionGetResDtos);
		
		return testGetResDto;
	}
	
	
	public InsertResDto assignTest(SkillTestQuestionInsertReqDto data) {		
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
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
				
				final String skillTestInsertCandidateAPI = "http://localhost:8081/skilltests/question";

				final HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setBearerAuth(JwtConfig.get());
				
				final RequestEntity<SkillTestQuestionInsertReqDto> skillTestInsert = RequestEntity.post(skillTestInsertCandidateAPI).headers(headers)
						.body(data);

				final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(skillTestInsert, InsertResDto.class);

				if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
					insertResDto.setMessage("Assign Test Success");
					em().getTransaction().commit();
				} else {
					em().getTransaction().rollback();
					throw new RuntimeException("Insert Failed");
				}
			}
			else {
				insertResDto.setMessage("Assign Test Failed");
			}
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		return insertResDto;
	}
	
	public UpdateResDto update(SkillTestUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			
			final SkillTest skillTest = skillTestDao.getById(SkillTest.class, data.getId());
			data.setTestCode(data.getTestCode());
			
			skillTest.setTestName(data.getTestName());
			
			final SkillTest result = skillTestDao.save(skillTest);
			
			updateResDto.setMessage("Skill Test Updated");
			updateResDto.setVersion(result.getVersion());		
			
			em().getTransaction().commit();		
			
		}catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		return updateResDto;
	}
  
	public InsertResDto insertSkillTest(SkillTestInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			final SkillTest skillTest = new SkillTest();
			final Job job = jobDao.getByCode(data.getJobCode());
			final String skillTestCode = GeneratorId.generateCode();
			data.setTestCode(skillTestCode);
			skillTest.setTestCode(skillTestCode);
			skillTest.setTestName(data.getTestName());
			skillTest.setJob(job);
			final SkillTest skillTestResult = skillTestDao.save(skillTest);
			
			final String skillTestInsertCandidateAPI = "http://localhost:8081/skilltests";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<SkillTestInsertReqDto> companyInsert = RequestEntity.post(skillTestInsertCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(companyInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(skillTestResult.getId());
				result.setMessage("Insert Skill Test Successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
			
			
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;
	}
	
	
}

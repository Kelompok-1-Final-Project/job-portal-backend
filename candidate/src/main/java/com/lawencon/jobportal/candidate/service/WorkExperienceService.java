package com.lawencon.jobportal.candidate.service;

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
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dao.WorkExperienceDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceGetResDto;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceInsertReqDto;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceUpdateReqDto;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.model.WorkExperience;
import com.lawencon.jobportal.candidate.util.DateConvert;

@Service
public class WorkExperienceService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private WorkExperienceDao workExperienceDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<WorkExperienceGetResDto> getByCandidate(String candidateId) {
		final List<WorkExperienceGetResDto> experienceGetResDtos = new ArrayList<>();

		workExperienceDao.getByCandidate(candidateId).forEach(we -> {
			final WorkExperienceGetResDto experienceGetResDto = new WorkExperienceGetResDto();
			experienceGetResDto.setId(we.getId());
			experienceGetResDto.setPositionName(we.getPositionName());
			experienceGetResDto.setCompanyName(we.getCompanyName());
			experienceGetResDto.setStartDate(we.getStartDate().toString());
			experienceGetResDto.setEndDate(we.getEndDate().toString());

			experienceGetResDtos.add(experienceGetResDto);
		});

		return experienceGetResDtos;
	}

	public InsertResDto insertWorkExperience(WorkExperienceInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final WorkExperience experience = new WorkExperience();
			
			final User candidate = userDao.getById(User.class, data.getCandidateId());
			data.setCandidateEmail(candidate.getEmail());
			experience.setCandidate(candidate);
			experience.setPositionName(data.getPositionName());
			experience.setCompanyName(data.getCompanyName());
			experience.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
			experience.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
			final WorkExperience experienceResult = workExperienceDao.save(experience);
			
			final String experienceInsertAdminAPI = "http://localhost:8080/work-experience";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<WorkExperienceInsertReqDto> experienceInsert = RequestEntity.post(experienceInsertAdminAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(experienceInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(experienceResult.getId());
				result.setMessage("Experience Successfully added.");
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

	public boolean deleteExperience(String experienceId) {
		em().getTransaction().begin();

		final Boolean result = workExperienceDao.deleteById(WorkExperience.class, experienceId);

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto updateWorkExperience(WorkExperienceUpdateReqDto data) {
		em().getTransaction().begin();

		final WorkExperience experience = workExperienceDao.getById(WorkExperience.class, data.getExperienceId());
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		experience.setCandidate(candidate);
		experience.setPositionName(data.getPositionName());
		experience.setCompanyName(data.getCompanyName());
		experience.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
		experience.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		final WorkExperience experienceResult = workExperienceDao.saveAndFlush(experience);
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(experienceResult.getVersion());
		result.setMessage("Experience Successfully Updated.");

		em().getTransaction().commit();
		return result;
	}
}

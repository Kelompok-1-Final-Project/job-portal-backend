package com.lawencon.jobportal.candidate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dao.WorkExperienceDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceGetResDto;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceInsertReqDto;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.model.WorkExperience;

@Service
public class WorkExperienceService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private WorkExperienceDao workExperienceDao;
	
	@Autowired
	private UserDao userDao;
	
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
	
	public InsertResDto insertWorkExperience(@RequestBody WorkExperienceInsertReqDto data) {
		em().getTransaction().begin();
		
		final WorkExperience experience = new WorkExperience();
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		experience.setCandidate(candidate);
		experience.setPositionName(data.getPositionName());
		experience.setCompanyName(data.getCompanyName());
		experience.setStartDate(LocalDate.parse(data.getStartDate()));
		experience.setEndDate(LocalDate.parse(data.getEndDate()));
		final WorkExperience experienceResult = workExperienceDao.save(experience);
		final InsertResDto result = new InsertResDto();
		result.setId(experienceResult.getId());
		result.setMessage("Experience Successfully added.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public boolean deleteExperience(String experienceId) {
		em().getTransaction().begin();
		
		final Boolean result = workExperienceDao.deleteById(WorkExperience.class, experienceId);
		
		em().getTransaction().commit();
		return result;
	}
}

package com.lawencon.jobportal.candidate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.util.DateConvert;
import com.lawencon.jobportal.candidate.dao.EducationDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.education.EducationGetResDto;
import com.lawencon.jobportal.candidate.dto.education.EducationInsertReqDto;
import com.lawencon.jobportal.candidate.dto.education.EducationUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Education;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class EducationService {

	@Autowired
	private EducationDao educationDao;

	@Autowired
	private UserDao userDao;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<EducationGetResDto> getByCandidate(String candidateId) {
		final List<EducationGetResDto> educationGetResDtos = new ArrayList<>();

		educationDao.getByCandidate(candidateId).forEach(e -> {
			final EducationGetResDto educationGetResDto = new EducationGetResDto();
			educationGetResDto.setId(e.getId());
			educationGetResDto.setEducationName(e.getEducationName());
			educationGetResDto.setStartDate(e.getStartDate().toString());
			educationGetResDto.setEndDate(e.getEndDate().toString());
			educationGetResDtos.add(educationGetResDto);
		});

		return educationGetResDtos;
	}

	public InsertResDto insertEducation(EducationInsertReqDto data) {
		em().getTransaction().begin();

		final Education education = new Education();
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		education.setCandidate(candidate);
		education.setEducationName(data.getEducationName());
		education.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
		education.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		final Education educationResult = educationDao.save(education);
		final InsertResDto result = new InsertResDto();
		result.setId(educationResult.getId());
		result.setMessage("Education Successfully added.");

		em().getTransaction().commit();
		return result;
	}

	public boolean deleteEducation(String educationId) {
		em().getTransaction().begin();

		final Boolean result = educationDao.deleteById(Education.class, educationId);

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto updateEducation(EducationUpdateReqDto data) {
		em().getTransaction().begin();

		final Education education = educationDao.getById(Education.class, data.getEducationId());
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		education.setCandidate(candidate);
		education.setEducationName(data.getEducationName());
		education.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
		education.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		final Education educationResult = educationDao.save(education);
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(educationResult.getVersion());
		result.setMessage("Education Successfully added.");

		em().getTransaction().commit();
		return result;
	}
}

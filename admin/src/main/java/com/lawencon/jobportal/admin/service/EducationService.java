package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.EducationDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.education.EducationGetResDto;
import com.lawencon.jobportal.admin.dto.education.EducationInsertReqDto;
import com.lawencon.jobportal.admin.dto.education.EducationUpdateReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Education;
import com.lawencon.jobportal.admin.util.DateConvert;

@Service
public class EducationService {

	@Autowired
	private EducationDao educationDao;

	@Autowired
	private CandidateDao candidateDao;

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
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
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
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		education.setCandidate(candidate);
		education.setEducationName(data.getEducationName());
		education.setStartDate(DateConvert.convertDate(data.getStartDate()).toLocalDate());
		education.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		final Education educationResult = educationDao.save(education);
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(educationResult.getVersion());
		result.setMessage("Education updated.");

		em().getTransaction().commit();
		return result;
	}
}

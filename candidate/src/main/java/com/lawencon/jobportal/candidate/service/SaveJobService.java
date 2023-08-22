package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.JobDao;
import com.lawencon.jobportal.candidate.dao.SaveJobDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.savejob.SaveJobGetResDto;
import com.lawencon.jobportal.candidate.dto.savejob.SaveJobInsertReqDto;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.SaveJob;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class SaveJobService {
	
	@Autowired
	private SaveJobDao saveJobDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JobDao jobDao;
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<SaveJobGetResDto> getByCandidate(String candidateId) {
		final List<SaveJobGetResDto> saveJobGetResDtos = new ArrayList<>();
		final Integer saveJobTotal = saveJobDao.getByCandidate(candidateId).size();
		
		saveJobDao.getByCandidate(candidateId).forEach(sj -> {
			final SaveJobGetResDto saveJobGetResDto = new SaveJobGetResDto();
			saveJobGetResDto.setId(sj.getId());
			saveJobGetResDto.setCompanyName(sj.getJob().getCompany().getCompanyName());
			saveJobGetResDto.setCompanyPhoto(sj.getJob().getCompany().getFile().getId());
			saveJobGetResDto.setDescription(sj.getJob().getDescription());
			saveJobGetResDto.setEmploymentName(sj.getJob().getEmployementType().getEmploymentName());
			saveJobGetResDto.setEndDate(sj.getJob().getEndDate().toString());
			saveJobGetResDto.setJobTitle(sj.getJob().getJobTitle());
			saveJobGetResDto.setPositionName(sj.getJob().getJobPosition().getPositionName());
			saveJobGetResDto.setSalaryEnd(sj.getJob().getSalaryEnd());
			saveJobGetResDto.setSalaryStart(sj.getJob().getSalaryStart());
			saveJobGetResDto.setStatusName(sj.getJob().getJobStatus().getStatusName());
			saveJobGetResDto.setJobId(sj.getJob().getId());
			saveJobGetResDto.setTotalSaveJob(saveJobTotal);
			saveJobGetResDto.setCreatedAt(sj.getCreatedBy());
			
			saveJobGetResDtos.add(saveJobGetResDto);
		});

		return saveJobGetResDtos;
	}
	
	public InsertResDto insertSaveJob(SaveJobInsertReqDto data) {
		em().getTransaction().begin();
		
		final SaveJob saveJob = new SaveJob();
		final User candidate = userDao.getById(User.class, data.getCandidateId());
		saveJob.setCandidate(candidate);
		final Job job = jobDao.getById(Job.class, data.getJobId());
		saveJob.setJob(job);
		final SaveJob saveJobResult = saveJobDao.save(saveJob);
		final InsertResDto result = new InsertResDto();
		result.setId(saveJobResult.getId());
		result.setMessage("Job Saved");
		
		em().getTransaction().commit();
		return result;
	}
	
	public boolean deleteSaveJob(String saveJobId) {
		em().getTransaction().begin();
		
		final Boolean result = saveJobDao.deleteById(SaveJob.class, saveJobId);
		
		em().getTransaction().commit();
		return result;
	}
}

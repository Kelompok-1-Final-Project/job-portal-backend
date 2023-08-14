package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.BenefitDao;
import com.lawencon.jobportal.candidate.dao.JobBenefitDao;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitGetResDto;
import com.lawencon.jobportal.candidate.dto.benefit.JobBenefitGetResDto;
import com.lawencon.jobportal.candidate.model.Benefit;
import com.lawencon.jobportal.candidate.model.JobBenefit;

@Service
public class BenefitService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private BenefitDao benefitDao;
	
	@Autowired
	private JobBenefitDao jobBenefitDao;
	
	public List<BenefitGetResDto> getAllBenefit(){
		final List<BenefitGetResDto> benefitGetResDtos = new ArrayList<>();
		benefitDao.getAll(Benefit.class).forEach(b -> {
			final BenefitGetResDto benefitGetResDto = new BenefitGetResDto();
			benefitGetResDto.setId(b.getId());
			benefitGetResDto.setBenefitName(b.getBenefitName());
			benefitGetResDto.setBenefitCode(b.getBenefitCode());
			benefitGetResDtos.add(benefitGetResDto);
		});
		return benefitGetResDtos;
	}
	
	public List<JobBenefitGetResDto> getByJob(String jobId){
		final List<JobBenefitGetResDto> listResult = new ArrayList<>();
		final List<JobBenefit> listJobBenefit = jobBenefitDao.getByJob(jobId);
		for(JobBenefit j:listJobBenefit) {
			final JobBenefitGetResDto result = new JobBenefitGetResDto();
			result.setId(j.getId());
			result.setBenefitName(j.getBenefit().getBenefitName());
			listResult.add(result);
		}
		return listResult;
	}
}

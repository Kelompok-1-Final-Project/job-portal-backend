package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.candidate.dao.BenefitDao;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitGetResDto;
import com.lawencon.jobportal.candidate.model.Benefit;

@Service
public class BenefitService {

	@Autowired
	private BenefitDao benefitDao;
	
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
}

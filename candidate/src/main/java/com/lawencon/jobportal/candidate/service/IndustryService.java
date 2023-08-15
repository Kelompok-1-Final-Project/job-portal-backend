package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.IndustryDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.industry.IndustryGetResDto;
import com.lawencon.jobportal.candidate.dto.industry.IndustryInsertReqDto;
import com.lawencon.jobportal.candidate.model.Industry;

@Service
public class IndustryService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private IndustryDao industryDao;

	public List<IndustryGetResDto> getAllIndustry() {
		final List<IndustryGetResDto> industryGetResDtos = new ArrayList<>();
		industryDao.getAll(Industry.class).forEach(i -> {
			final IndustryGetResDto industryGetResDto = new IndustryGetResDto();
			industryGetResDto.setId(i.getId());
			industryGetResDto.setIndustryName(i.getIndustryName());
			industryGetResDto.setIndustryCode(i.getIndustryCode());
			industryGetResDtos.add(industryGetResDto);
		});
		return industryGetResDtos;
	}

	public IndustryGetResDto getByName(String name) {
		final IndustryGetResDto industryGetResDto = new IndustryGetResDto();
		final Industry industry = industryDao.getByName(name);
		industryGetResDto.setId(industry.getId());
		industryGetResDto.setIndustryName(industry.getIndustryName());
		industryGetResDto.setIndustryCode(industry.getIndustryCode());
		return industryGetResDto;
	}
	
	public InsertResDto insert(IndustryInsertReqDto data) {
		em().getTransaction().begin();
		
		final Industry industry = new Industry();
		industry.setIndustryCode(data.getIndustryCode());
		industry.setIndustryName(data.getIndustryName());
		final Industry industries = industryDao.save(industry);
		
		final InsertResDto result = new InsertResDto();
		result.setId(industries.getId());
		result.setMessage("Industry added successfully");
		
		em().getTransaction().commit();
		return result;
	}
}

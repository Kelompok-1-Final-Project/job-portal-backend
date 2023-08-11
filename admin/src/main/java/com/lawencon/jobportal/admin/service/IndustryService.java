package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.IndustryDao;
import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.industry.IndustryGetResDto;
import com.lawencon.jobportal.admin.dto.industry.IndustryInsertReqDto;
import com.lawencon.jobportal.admin.dto.industry.IndustryUpdateReqDto;
import com.lawencon.jobportal.admin.model.Benefit;
import com.lawencon.jobportal.admin.model.Industry;

@Service
public class IndustryService{
	public String createdBy="0";
	
	@Autowired
	private IndustryDao industryDao;
	
	
	public InsertResDto insert(IndustryInsertReqDto data) {
		final String industryCode = generateCode();
		final Industry industry = new Industry();
		industry.setIndustryCode(industryCode);
		industry.setIndustryName(data.getIndustryName());
		industry.setCreatedBy(createdBy);
		final Industry industries = industryDao.save(industry);
		
		final InsertResDto result = new InsertResDto();
		result.setId(industries.getId());
		result.setMessage("Success add industry");
		return result;
	}
	
	public List<IndustryGetResDto> getAllIndustry(){
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
	
	public IndustryGetResDto getByName(String name){
			final IndustryGetResDto industryGetResDto = new IndustryGetResDto();
			final Industry industry = industryDao.getByName(name);
			industryGetResDto.setId(industry.getId());
			industryGetResDto.setIndustryName(industry.getIndustryName());
			industryGetResDto.setIndustryCode(industry.getIndustryCode());
			return industryGetResDto;
	}
	
	
	public UpdateResDto update(IndustryUpdateReqDto dto) {
		Industry industryResult = new Industry();
		final Industry industryDb = industryDao.getByCode(dto.getIndustryCode());
		final Industry industryUpdate = industryDao.getById(Industry.class, industryDb.getId());
		
		industryUpdate.setIndustryName(dto.getIndustryName());
		industryResult = industryDao.saveAndFlush(industryUpdate);

		final UpdateResDto response = new UpdateResDto();
		response.setVersion(industryResult.getVersion());
		response.setMessage("Berhasil Update Industry");
		return response;
	}
	
	
	public DeleteResDto deleteByCode(String code) {
		boolean checkUpdate;
		final Industry industryDb = industryDao.getByCode(code);
		final Industry industryDelete = industryDao.getById(Industry.class, industryDb.getId());
		final DeleteResDto response = new DeleteResDto();
		
		checkUpdate=industryDao.deleteById(Benefit.class, industryDelete);
		
		if(checkUpdate) {
			response.setMessage("Successful delete data");			
		}else {
			response.setMessage("Failed to delete data");
		}
		return response;
	}
}

package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
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
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private IndustryDao industryDao;
	
	
	public InsertResDto insert(IndustryInsertReqDto data) {
		em().getTransaction().begin();
		
		final String industryCode = generateCode();
		final Industry industry = new Industry();
		industry.setIndustryCode(industryCode);
		industry.setIndustryName(data.getIndustryName());
		final Industry industries = industryDao.save(industry);
		
		final InsertResDto result = new InsertResDto();
		result.setId(industries.getId());
		result.setMessage("Industry added successfully");
		
		em().getTransaction().commit();
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
		em().getTransaction().begin();
		
		Industry industryResult = new Industry();
		final Industry industryDb = industryDao.getByCode(dto.getIndustryCode());
		final Industry industryUpdate = industryDao.getById(Industry.class, industryDb.getId());
		
		industryUpdate.setIndustryName(dto.getIndustryName());
		industryResult = industryDao.saveAndFlush(industryUpdate);

		final UpdateResDto response = new UpdateResDto();
		response.setVersion(industryResult.getVersion());
		response.setMessage("Industry updated successfully");
		
		em().getTransaction().commit();
		return response;
	}
	
	
	public DeleteResDto deleteByCode(String code) {
		em().getTransaction().begin();
		
		boolean checkUpdate;
		final Industry industryDb = industryDao.getByCode(code);
		final Industry industryDelete = industryDao.getById(Industry.class, industryDb.getId());
		final DeleteResDto response = new DeleteResDto();
		
		checkUpdate=industryDao.deleteById(Benefit.class, industryDelete);
		
		if(checkUpdate) {
			response.setMessage("Data deleted successfully");			
		}else {
			response.setMessage("Failed to delete data");
		}
		
		em().getTransaction().commit();
		return response;
	}
}

package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.BenefitDao;
import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.benefit.BenefitGetResDto;
import com.lawencon.jobportal.admin.dto.benefit.BenefitInsertReqDto;
import com.lawencon.jobportal.admin.dto.benefit.BenefitUpdateReqDto;
import com.lawencon.jobportal.admin.model.Benefit;

@Service
public class BenefitService {
public String createdBy="0";
	
	@Autowired
	private BenefitDao benefitDao;
	
	
	public InsertResDto insert(BenefitInsertReqDto data) {
		final String benefitCode = generateCode();
		final Benefit benefit = new Benefit();
		benefit.setBenefitCode(benefitCode);
		benefit.setBenefitName(data.getBenefitName());
		benefit.setCreatedBy(createdBy);
		final Benefit benefits = benefitDao.save(benefit);
		
		final InsertResDto result = new InsertResDto();
		result.setId(benefits.getId());
		result.setMessage("successful  add benefit");
		return result;
	}
	
	
	public UpdateResDto update(BenefitUpdateReqDto dto) {
		Benefit benefitResult = new Benefit();
		final Benefit benefitDb = benefitDao.getByCode(dto.getBenefitCode());
		final Benefit benefitUpdate = benefitDao.getById(Benefit.class, benefitDb.getId());
		
		benefitUpdate.setBenefitName(dto.getBenefitName());
		benefitResult = benefitDao.saveAndFlush(benefitUpdate);

		final UpdateResDto response = new UpdateResDto();
		response.setVersion(benefitResult.getVersion());
		response.setMessage("Berhasil Update Benefit");
		return response;
	}
	
	
	public DeleteResDto deleteByCode(String code) {
		final Benefit benefitDb = benefitDao.getByCode(code);
		final Benefit benefitDelete = benefitDao.getById(Benefit.class, benefitDb.getId());
		final DeleteResDto response = new DeleteResDto();
		
		if(benefitDao.deleteById(Benefit.class, benefitDelete)) {
			response.setMessage("Successful delete data");			
		}else {
			response.setMessage("Failed to delete data");
		}
		return response;
	}
	
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
	
	public BenefitGetResDto getByName(String name){
			final BenefitGetResDto BenefitGetResDto = new BenefitGetResDto();
			final Benefit benefit = benefitDao.getByName(name);
			BenefitGetResDto.setId(benefit.getId());
			BenefitGetResDto.setBenefitName(benefit.getBenefitName());
			BenefitGetResDto.setBenefitCode(benefit.getBenefitCode());
			return BenefitGetResDto;
		}
}

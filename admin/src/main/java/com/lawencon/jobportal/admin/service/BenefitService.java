package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
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

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private BenefitDao benefitDao;

	public InsertResDto insert(BenefitInsertReqDto data) {
		em().getTransaction().begin();

		final String benefitCode = generateCode();
		final Benefit benefit = new Benefit();
		benefit.setBenefitCode(benefitCode);
		benefit.setBenefitName(data.getBenefitName());
		final Benefit benefits = benefitDao.save(benefit);

		final InsertResDto result = new InsertResDto();
		result.setId(benefits.getId());
		result.setMessage("Benefit added successfully");

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto update(BenefitUpdateReqDto dto) {
		em().getTransaction().begin();

		Benefit benefitResult = new Benefit();
		final Benefit benefitDb = benefitDao.getByCode(dto.getBenefitCode());
		final Benefit benefitUpdate = benefitDao.getById(Benefit.class, benefitDb.getId());

		benefitUpdate.setBenefitName(dto.getBenefitName());
		benefitResult = benefitDao.saveAndFlush(benefitUpdate);

		final UpdateResDto response = new UpdateResDto();
		response.setVersion(benefitResult.getVersion());
		response.setMessage("Benefit updated successfully");

		em().getTransaction().commit();
		return response;
	}

	public DeleteResDto deleteByCode(String code) {
		em().getTransaction().begin();

		final Benefit benefitDb = benefitDao.getByCode(code);
		final Benefit benefitDelete = benefitDao.getById(Benefit.class, benefitDb.getId());
		final DeleteResDto response = new DeleteResDto();

		if (benefitDao.deleteById(Benefit.class, benefitDelete)) {
			response.setMessage("Data deleted successfully");
		} else {
			response.setMessage("Failed to delete data");
		}

		em().getTransaction().commit();
		return response;
	}

	public List<BenefitGetResDto> getAllBenefit() {
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

	public BenefitGetResDto getByName(String name) {
		final BenefitGetResDto BenefitGetResDto = new BenefitGetResDto();
		final Benefit benefit = benefitDao.getByName(name);
		BenefitGetResDto.setId(benefit.getId());
		BenefitGetResDto.setBenefitName(benefit.getBenefitName());
		BenefitGetResDto.setBenefitCode(benefit.getBenefitCode());
		return BenefitGetResDto;
	}
	
	//insert
	public Boolean valIdNull(Benefit benefit) {
		if(benefit.getId() == null) {
			return true;
		}
		return false;
	}
	
//	public Boolean valBkNotNull(Benefit benefit) {
//		return true;
//	}
	
	public Boolean valBkNotExist(Benefit benefit) {
		return true;
	}
	
	public Boolean valNonBk(Benefit benefit) {
		return true;
	}
	
	//update
	
	public Boolean valIdNotNull(Benefit benefit) {
		if(benefit.getId() == null) {
			return false;
		}
		return true;
	}
	
	public Boolean valIdExist(String benefitId) {
		final Benefit benefit = benefitDao.getByIdRef(Benefit.class, benefitId);
		if(benefit == null) {
			return false;
		}
		return true;
	}
	
	public Boolean valBkNotNull(Benefit benefit) {
		return true;
	}
	
	public Boolean valBKNotChange(Benefit benefit) {
		return true;
	}
	
	public Boolean valNotBk(Benefit benefit) {
		return true;
	}
}

package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
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

	@Autowired
	private RestTemplate restTemplate;
	
	public InsertResDto insert(BenefitInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final String benefitCode = generateCode();
			data.setBenefitCode(benefitCode);
			final Benefit benefit = new Benefit();
			benefit.setBenefitCode(benefitCode);
			benefit.setBenefitName(data.getBenefitName());
			final Benefit benefits = benefitDao.save(benefit);

			
			result.setId(benefits.getId());
			result.setMessage("Benefit added successfully");

			final String benefitInsertCandidateAPI = "http://localhost:8081/benefits";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<BenefitInsertReqDto> companyInsert = RequestEntity.post(benefitInsertCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(companyInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(benefits.getId());
				result.setMessage("City added successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
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

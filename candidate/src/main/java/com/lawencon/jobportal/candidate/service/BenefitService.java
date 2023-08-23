package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.BenefitDao;
import com.lawencon.jobportal.candidate.dao.JobBenefitDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitGetResDto;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitInsertReqDto;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitUpdateReqDto;
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

	public List<JobBenefitGetResDto> getByJob(String jobId) {
		final List<JobBenefitGetResDto> listResult = new ArrayList<>();
		final List<JobBenefit> listJobBenefit = jobBenefitDao.getByJob(jobId);
		for (JobBenefit j : listJobBenefit) {
			final JobBenefitGetResDto result = new JobBenefitGetResDto();
			result.setId(j.getId());
			result.setBenefitName(j.getBenefit().getBenefitName());
			listResult.add(result);
		}
		return listResult;
	}

	public InsertResDto insert(BenefitInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Benefit benefit = new Benefit();
			benefit.setBenefitCode(data.getBenefitCode());
			benefit.setBenefitName(data.getBenefitName());
			final Benefit benefits = benefitDao.save(benefit);

			result.setId(benefits.getId());
			result.setMessage("Benefit added successfully");

			em().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}

		return result;
	}

	public UpdateResDto update(BenefitUpdateReqDto dto) {
		final UpdateResDto response = new UpdateResDto();
		try {
			em().getTransaction().begin();

			Benefit benefitResult = new Benefit();
			final Benefit benefitDb = benefitDao.getByCode(dto.getBenefitCode());
			final Benefit benefitId = benefitDao.getById(Benefit.class, benefitDb.getId());

			benefitId.setBenefitName(dto.getBenefitName());
			benefitResult = benefitDao.save(benefitId);

			response.setVersion(benefitResult.getVersion());
			response.setMessage("Benefit updated successfully");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return response;
	}
}

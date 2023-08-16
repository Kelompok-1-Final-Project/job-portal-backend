package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.CityDao;
import com.lawencon.jobportal.candidate.dao.CompanyDao;
import com.lawencon.jobportal.candidate.dao.FileDao;
import com.lawencon.jobportal.candidate.dao.IndustryDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.company.CompanyGetResDto;
import com.lawencon.jobportal.candidate.dto.company.CompanyInsertReqDto;
import com.lawencon.jobportal.candidate.dto.company.CompanyUpdateReqDto;
import com.lawencon.jobportal.candidate.model.City;
import com.lawencon.jobportal.candidate.model.Company;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Industry;

@Service
public class CompanyService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private IndustryDao industryDao;

	@Autowired
	private CityDao cityDao;

	public List<CompanyGetResDto> getAll() {
		final List<Company> listCompany = companyDao.getAll(Company.class);
		final List<CompanyGetResDto> listResult = new ArrayList<>();
		for (Company c : listCompany) {
			final CompanyGetResDto result = new CompanyGetResDto();
			result.setCompanyId(c.getId());
			result.setCompanyCode(c.getCompanyCode());
			result.setCompanyName(c.getCompanyName());
			result.setFileId(c.getFile().getId());
			result.setIndustryName(c.getIndustry().getIndustryName());
			listResult.add(result);
		}
		return listResult;
	}

	public InsertResDto insertCompany(CompanyInsertReqDto data) {
		final InsertResDto result = new InsertResDto();

		try {

			em().getTransaction().begin();

			final File file = new File();
			file.setExt(data.getExt());
			file.setFile(data.getFile());
			final File files = fileDao.save(file);

			final Industry industry = industryDao.getByCode(data.getIndustryCode());
			final City city = cityDao.getByCode(data.getCityCode());
			final Company company = new Company();
			company.setCompanyName(data.getCompanyName());
			company.setCompanyCode(data.getCompanyCode());
			company.setIndustry(industry);
			company.setCity(city);
			company.setFile(files);
			final Company companies = companyDao.save(company);

			result.setId(companies.getId());
			result.setMessage("Company added successfully.");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		return result;
	}

	public UpdateResDto updateCompany(CompanyUpdateReqDto data) {
		em().getTransaction().begin();

		final Company companyDb = companyDao.getByCode(data.getCompanyCode());
		final Company company = companyDao.getById(Company.class, companyDb.getId());

		final File file = new File();
		file.setExt(data.getExt());
		file.setFile(data.getFile());
		final File files = fileDao.save(file);

		final Industry industryDb = industryDao.getByCode(data.getIndustryCode());
		final Industry industry = industryDao.getById(Industry.class, industryDb.getId());
		company.setCompanyName(data.getCompanyName());
		company.setAddress(data.getAddress());
		company.setDescription(data.getDescription());
		company.setFile(files);
		company.setIndustry(industry);
		final Company companyResult = companyDao.saveAndFlush(company);

		final UpdateResDto result = new UpdateResDto();
		result.setVersion(companyResult.getVersion());
		result.setMessage("Company updated successfully.");
		em().getTransaction().commit();

		return result;
	}
}

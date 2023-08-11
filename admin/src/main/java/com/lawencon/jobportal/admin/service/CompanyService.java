package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CompanyDao;
import com.lawencon.jobportal.admin.dao.FileDao;
import com.lawencon.jobportal.admin.dao.IndustryDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.company.CompanyGetResDto;
import com.lawencon.jobportal.admin.dto.company.CompanyInsertReqDto;
import com.lawencon.jobportal.admin.dto.company.CompanyUpdateReqDto;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Industry;

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
	
	
	public InsertResDto insertCompany(CompanyInsertReqDto data) {
		em().getTransaction().begin();
		
		final String companyCode = generateCode();
		final File file = new File();
		file.setExt(data.getExt());
		file.setFile(data.getFile());
		final File files = fileDao.save(file);
		
		final Industry industry = industryDao.getByCode(data.getIndustryCode());
		final Company company = new Company();
		company.setCompanyName(data.getCompanyName());
		company.setCompanyCode(companyCode);
		company.setIndustry(industry);
		company.setFile(files);
		final Company companies = companyDao.save(company);
		
		final InsertResDto result = new InsertResDto();
		result.setId(companies.getId());
		result.setMessage("Company added successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public List<CompanyGetResDto> getAll(){
		final List<Company> listCompany = companyDao.getAll(Company.class);
		final List<CompanyGetResDto> listResult = new ArrayList<>();
		for(Company c:listCompany) {
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
		company.setFile(files);
		company.setIndustry(industry);
		final Company companyResult = companyDao.saveAndFlush(company);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(companyResult.getVersion());
		result.setMessage("Company updated successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public boolean deleteCompany(String companyCode) {
		em().getTransaction().begin();
		
		final Company company = companyDao.getByCode(companyCode);
		final Boolean result = companyDao.deleteById(Company.class, company.getId());
		
		em().getTransaction().commit();
		return result;
	}
}





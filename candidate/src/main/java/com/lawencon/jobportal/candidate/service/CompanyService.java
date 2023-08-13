package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.candidate.dao.CompanyDao;
import com.lawencon.jobportal.candidate.dto.company.CompanyGetResDto;
import com.lawencon.jobportal.candidate.model.Company;

@Service
public class CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
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
}

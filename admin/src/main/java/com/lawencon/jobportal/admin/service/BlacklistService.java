package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.constant.StatusCodeEnum;
import com.lawencon.jobportal.admin.dao.BlacklistDao;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.CompanyDao;
import com.lawencon.jobportal.admin.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.blacklist.BlacklistGetResDto;
import com.lawencon.jobportal.admin.dto.blacklist.BlacklistInsertReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressUpdateReqDto;
import com.lawencon.jobportal.admin.model.Blacklist;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.JobCandidateStatus;

@Service
public class BlacklistService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private BlacklistDao blacklistDao;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;
	
	@Autowired
	private ProgressStatusService progressStatusService;
	
	public List<BlacklistGetResDto> getAll(){
		final List<BlacklistGetResDto> blacklistGetResDtos = new ArrayList<>();

		blacklistDao.getAll(Blacklist.class).forEach(b -> {
			final BlacklistGetResDto blacklistGetResDto = new BlacklistGetResDto();
			blacklistGetResDto.setBlacklistId(b.getId());
			blacklistGetResDto.setCandidateId(b.getCandidate().getId());
			blacklistGetResDto.setCandidateName(b.getCandidate().getCandidateProfile().getFullName());
			blacklistGetResDto.setCompanyId(b.getCompany().getId());
			blacklistGetResDto.setCompanyName(b.getCompany().getCompanyName());
			blacklistGetResDtos.add(blacklistGetResDto);
		});

		return blacklistGetResDtos;
	}
	
	public InsertResDto insertBlacklist(BlacklistInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Company company = companyDao.getById(Company.class, data.getCompanyId());
		
		final Blacklist blacklist = new Blacklist();
		blacklist.setCandidate(candidate);
		blacklist.setCompany(company);
		
		final Blacklist blacklistResult = blacklistDao.save(blacklist);
		
		final InsertResDto result = new InsertResDto();
		result.setId(blacklistResult.getId());
		result.setMessage("Insert Blacklist Successfully.");

		em().getTransaction().commit();

		final JobCandidateStatus jobCandidateStatus = jobCandidateStatusDao.getByCandidateAndCompany(candidate.getEmail(),
				company.getCompanyCode());

		final CandidateProgressUpdateReqDto dataSend = new CandidateProgressUpdateReqDto();
		dataSend.setStatusProcessCode(StatusCodeEnum.BLACKLIST.processCode);
		dataSend.setCandidateProgressId(jobCandidateStatus.getId());

		progressStatusService.updateCandidateProgress(dataSend);

		return result;
	}
}

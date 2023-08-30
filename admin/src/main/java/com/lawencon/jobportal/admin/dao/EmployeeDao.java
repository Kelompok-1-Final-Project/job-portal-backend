package com.lawencon.jobportal.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.Employee;

@Repository
public class EmployeeDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	public List<Employee> getAllNotBlacklist(){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT te.id AS employee_id, tca.id AS candidate_id, tcp.full_name, tc.id, tc.company_name ");
		sql.append("FROM t_employee te ");
		sql.append("INNER JOIN t_candidate tca ON te.candidate_id = tca.id ");
		sql.append("INNER JOIN t_candidate_profile tcp ON tca.profile_id  = tcp.id ");
		sql.append("INNER JOIN t_company tc ON te.company_id = tc.id ");
		sql.append("WHERE te.candidate_id NOT IN (SELECT tb.candidate_id FROM t_blacklist tb) ");
		
		final List<?> employeeObjs = this.em().createNativeQuery(sql.toString())
				.getResultList();
		
		final List<Employee> listEmployee = new ArrayList<>();
		if(employeeObjs.size()>0) {
			for(Object employeeObj : employeeObjs) {
				final Object[] employeeArr = (Object[]) employeeObj;
				final Employee employee = new Employee();
				employee.setId(employeeArr[0].toString());
				
				final Candidate candidate = new Candidate();
				candidate.setId(employeeArr[1].toString());
				
				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(employeeArr[2].toString());
				candidate.setCandidateProfile(candidateProfile);
				employee.setCandidate(candidate);
				
				final Company company = new Company();
				company.setId(employeeArr[3].toString());
				company.setCompanyName(employeeArr[4].toString());
				employee.setCompany(company);
				
				listEmployee.add(employee);
			}
		}
		return listEmployee;
	}
}

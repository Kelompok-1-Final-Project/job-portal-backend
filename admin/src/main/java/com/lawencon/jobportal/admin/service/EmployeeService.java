package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.EmployeeDao;
import com.lawencon.jobportal.admin.dto.employee.EmployeeGetResDto;
import com.lawencon.jobportal.admin.model.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	public List<EmployeeGetResDto> getAll() {
		final List<Employee> listEmployee = employeeDao.getAllNotBlacklist();
		final List<EmployeeGetResDto> listResult = new ArrayList<>();
		for (Employee e : listEmployee) {
			final EmployeeGetResDto employeeGetResDto = new EmployeeGetResDto();
			employeeGetResDto.setEmployeeId(e.getId());
			employeeGetResDto.setCandidateId(e.getCandidate().getId());
			employeeGetResDto.setCandidateName(e.getCandidate().getCandidateProfile().getFullName());
			employeeGetResDto.setCompanyId(e.getCompany().getId());
			employeeGetResDto.setCompanyName(e.getCompany().getCompanyName());
			listResult.add(employeeGetResDto);
		}
		return listResult;
	}
}

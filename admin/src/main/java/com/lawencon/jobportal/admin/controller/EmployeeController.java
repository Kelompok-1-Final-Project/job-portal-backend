package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.employee.EmployeeGetResDto;
import com.lawencon.jobportal.admin.service.EmployeeService;

@RestController
@RequestMapping("employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public ResponseEntity<List<EmployeeGetResDto>> getAllNotBlacklist(){
		final List<EmployeeGetResDto> response = employeeService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.admin.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobUpdateReqDto;
import com.lawencon.jobportal.admin.service.JobService;

@RestController
@RequestMapping("jobs")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping
	public ResponseEntity<List<JobGetResDto>> getAll() {
		final List<JobGetResDto> data = jobService.getAll();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/name")
	public ResponseEntity<List<JobGetResDto>> getByName(@RequestParam("n") String jobName) {
		final List<JobGetResDto> data = jobService.getByName(jobName);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/location")
	public ResponseEntity<List<JobGetResDto>> getByLocation(@RequestParam("loc") String location) {
		final List<JobGetResDto> data = jobService.getByLocation(location);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/company")
	public ResponseEntity<List<JobGetResDto>> getByCompany(@RequestParam("com") String company) {
		final List<JobGetResDto> data = jobService.getByCompany(company);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/type")
	public ResponseEntity<List<JobGetResDto>> getByType(@RequestParam("type") String type) {
		final List<JobGetResDto> data = jobService.getByEmploymentType(type);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/position")
	public ResponseEntity<List<JobGetResDto>> getByPosition(@RequestParam("pos") String postion) {
		final List<JobGetResDto> data = jobService.getByPosition(postion);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/status")
	public ResponseEntity<List<JobGetResDto>> getByStatus(@RequestParam("stat") String status) {
		final List<JobGetResDto> data = jobService.getByStatus(status);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("job-status")
	public ResponseEntity<List<JobStatusGetResDto>> getAllJobStatus() {
		final List<JobStatusGetResDto> data = jobService.getAllJobStatus();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("job-status/filter")
	public ResponseEntity<JobStatusGetResDto> getJobStatusByCode(@RequestParam("code") String code) {
		final JobStatusGetResDto data = jobService.getJobStatusByCode(code);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertJob(@RequestBody JobInsertReqDto data){
		final InsertResDto response = jobService.insertJob(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateJob(@RequestBody JobUpdateReqDto data){
		final UpdateResDto response = jobService.updateJob(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteJob(@RequestParam String jobId){
		final Boolean result = jobService.deleteJob(jobId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Delete Job Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Job Failed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("employment-type")
	public ResponseEntity<List<EmploymentTypeGetResDto>> getAllEmploymentType(){
		final List<EmploymentTypeGetResDto> response = jobService.getAllEmploymentType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

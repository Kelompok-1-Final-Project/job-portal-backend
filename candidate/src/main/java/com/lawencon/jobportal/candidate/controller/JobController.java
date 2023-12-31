package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.DeleteResDto;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobBenefitReqDto;
import com.lawencon.jobportal.candidate.dto.job.JobGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.candidate.dto.job.JobPositionGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobUpdateReqDto;
import com.lawencon.jobportal.candidate.service.JobService;

@RestController
@RequestMapping("jobs")
public class JobController {

	@Autowired
	private JobService jobService;
	
	@GetMapping("filter/location")
	public ResponseEntity<List<JobGetResDto>> getByLocation(@RequestParam("loc") String location) {
		final List<JobGetResDto> data = jobService.getByLocation(location);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filter/industry")
	public ResponseEntity<List<JobGetResDto>> getByIndustry(@RequestParam("ind") String industry, 
			@RequestParam("start") Integer startIndex, @RequestParam("end") Integer endIndex) {
		final List<JobGetResDto> data = jobService.getByIndustry(industry, startIndex, endIndex);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("filternonpagination/industry")
	public ResponseEntity<List<JobGetResDto>> getByIndustryNonPagination(@RequestParam("ind") String industry) {
		final List<JobGetResDto> data = jobService.getByIndustryWithoutPagination(industry);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("job-status")
	public ResponseEntity<List<JobStatusGetResDto>> getAllJobStatus() {
		final List<JobStatusGetResDto> data = jobService.getAllJobStatus();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("job-position")
	public ResponseEntity<List<JobPositionGetResDto>> getAllJobPosition(){
		final List<JobPositionGetResDto> response = jobService.getAllJobPosition();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("employment-type")
	public ResponseEntity<List<EmploymentTypeGetResDto>> getAllEmploymentType(){
		final List<EmploymentTypeGetResDto> response = jobService.getAllEmploymentType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertJob(@RequestBody JobInsertReqDto data){
		final InsertResDto response = jobService.insertJob(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<JobGetResDto>> getByStatus(@RequestParam("n") String name, 
			@RequestParam("c") String city, @RequestParam("p") String position, @RequestParam("e") List<String> employment, 
			@RequestParam("ss") Integer salaryStart, @RequestParam("se") Integer salaryEnd, @RequestParam("u") String user,
			@RequestParam("start") Integer startIndex, @RequestParam("end") Integer endIndex) {
		final List<JobGetResDto> data = jobService.getFilter(name, city, position, employment, salaryStart, salaryEnd, user, startIndex, endIndex);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/filternonpagination")
	public ResponseEntity<List<JobGetResDto>> getByFilterWithoutPagination(@RequestParam("n") String name, 
			@RequestParam("c") String city, @RequestParam("p") String position, @RequestParam("e") List<String> employment, 
			@RequestParam("ss") Integer salaryStart, @RequestParam("se") Integer salaryEnd, @RequestParam("u") String user) {
		final List<JobGetResDto> data = jobService.getFilterWithoutPagination(name, city, position, employment, salaryStart, salaryEnd, user);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/filter/id")
	public ResponseEntity<JobGetResDto> getById(@RequestParam("id") String jobId, @RequestParam("can") String candidateId){
		final JobGetResDto response = jobService.getById(jobId, candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/filter/code")
	public ResponseEntity<JobGetResDto> getByCode(@RequestParam("code") String jobCode, @RequestParam("can") String candidateId){
		final JobGetResDto response = jobService.getJobByCode(jobCode, candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateJob(@RequestBody JobUpdateReqDto data){
		final UpdateResDto response = jobService.updateJob(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("{j}/{b}")
	public ResponseEntity<DeleteResDto> deleteJobBenefit(@PathVariable("j") String jobCode, @PathVariable("b") String benefitCode) {
		final DeleteResDto result = jobService.deleteJobBenefit(jobCode, benefitCode);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{st}/{q}")
	public ResponseEntity<DeleteResDto> deleteSkillTestQuestion(@PathVariable("st") String skillTestCode, @PathVariable("q") String questionCode) {
		final DeleteResDto result = jobService.deleteSkillTestQuestion(skillTestCode, questionCode);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/job-benefit")
	public ResponseEntity<InsertResDto> insertJobBenfit(@RequestBody JobBenefitReqDto data) {
		final InsertResDto result = jobService.insertJobBenefit(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}

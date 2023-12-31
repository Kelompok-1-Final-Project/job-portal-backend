package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.application.ApplicationGetResDto;
import com.lawencon.jobportal.admin.dto.application.ApplicationInsertReqDto;
import com.lawencon.jobportal.admin.dto.assessment.AssessmentGetResDto;
import com.lawencon.jobportal.admin.dto.assessment.AssessmentInsertReqDto;
import com.lawencon.jobportal.admin.dto.assessment.AssessmentUpdateReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressGetResDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressInsertReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressUpdateReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateStageProcessResDto;
import com.lawencon.jobportal.admin.dto.hired.HiredGetResDto;
import com.lawencon.jobportal.admin.dto.hired.HiredInsertReqDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewGetResDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewInsertReqDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewUpdateReqDto;
import com.lawencon.jobportal.admin.dto.jobcandidatestatus.JobCandidateStatusGetReqDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupGetResDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupInsertReqDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupUpdateReqDto;
import com.lawencon.jobportal.admin.dto.offering.OfferingGetResDto;
import com.lawencon.jobportal.admin.dto.offering.OfferingInsertReqDto;
import com.lawencon.jobportal.admin.dto.progress.StatusProgressGetResDto;
import com.lawencon.jobportal.admin.service.ProgressStatusService;

@RestController
@RequestMapping("status-progress")
public class StatusProgressController {

	@Autowired
	private ProgressStatusService progressStatusService;
	
	@GetMapping
	public ResponseEntity<List<StatusProgressGetResDto>>getAllStatus(){
		final List<StatusProgressGetResDto> response = progressStatusService.getAllProgressStatus();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/candidate")
	public ResponseEntity<InsertResDto> insertCandidateProgress(@RequestBody CandidateProgressInsertReqDto data){
		final InsertResDto response = progressStatusService.insertProgressStatusCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping("/candidate")
	public ResponseEntity<UpdateResDto> updateCandidateProgress(@RequestBody CandidateProgressUpdateReqDto data){
		final UpdateResDto response = progressStatusService.updateCandidateProgress(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/candidate")
	public ResponseEntity<List<CandidateProgressGetResDto>>getAllCandidateProgress(){
		final List<CandidateProgressGetResDto> response = progressStatusService.getAllCandidateProgress();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/application")
	public ResponseEntity<List<ApplicationGetResDto>> getAllApplication(@RequestParam("user") String userId){
		final List<ApplicationGetResDto> response = progressStatusService.getAllApplication(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/assessment")
	public ResponseEntity<List<AssessmentGetResDto>> getAllAssessment(@RequestParam("user") String userId){
		final List<AssessmentGetResDto> response = progressStatusService.getAllAssessment(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/medical-checkup")
	public ResponseEntity<List<MedicalCheckupGetResDto>> getAllMedicalCheckup(@RequestParam("user") String userId){
		final List<MedicalCheckupGetResDto> response = progressStatusService.getAllMedicalCheckup(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/interview")
	public ResponseEntity<List<InterviewGetResDto>> getAllInterview(@RequestParam("user") String userId){
		final List<InterviewGetResDto> response = progressStatusService.getAllInterview(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/hired")
	public ResponseEntity<List<HiredGetResDto>> getAllHired(@RequestParam("user") String userId){
		final List<HiredGetResDto> response = progressStatusService.getAllHired(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/offering")
	public ResponseEntity<List<OfferingGetResDto>> getAllOffering(@RequestParam("user") String userId){
		final List<OfferingGetResDto> response = progressStatusService.getAllOffering(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/application")
	public ResponseEntity<InsertResDto> insertApplication(@RequestBody ApplicationInsertReqDto data){
		final InsertResDto response = progressStatusService.insertApplication(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/assessment")
	public ResponseEntity<InsertResDto> insertAssessment(@RequestBody AssessmentInsertReqDto data){
		final InsertResDto response = progressStatusService.insertAssessment(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/medical-checkup")
	public ResponseEntity<InsertResDto> insertMedicalCheckup(@RequestBody MedicalCheckupInsertReqDto data){
		final InsertResDto response = progressStatusService.insertMedicalCheckup(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/interview")
	public ResponseEntity<InsertResDto> insertInterview(@RequestBody InterviewInsertReqDto data){
		final InsertResDto response = progressStatusService.insertInterview(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/offering")
	public ResponseEntity<InsertResDto> insertOffering(@RequestBody OfferingInsertReqDto data){
		final InsertResDto response = progressStatusService.insertOffering(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/hired")
	public ResponseEntity<InsertResDto> insertHired(@RequestBody HiredInsertReqDto data){
		final InsertResDto response = progressStatusService.insertHired(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping("/rejected")
	public ResponseEntity<UpdateResDto> insertRejected(@RequestBody CandidateProgressUpdateReqDto data){
		final UpdateResDto response = progressStatusService.updateCandidateProgress(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/assessment")
	public ResponseEntity<UpdateResDto> updateAssessmentNotes(@RequestBody AssessmentUpdateReqDto data){
		final UpdateResDto response = progressStatusService.updateNotesAssessment(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/interview")
	public ResponseEntity<UpdateResDto> updateInterviewNotes(@RequestBody InterviewUpdateReqDto data){
		final UpdateResDto response = progressStatusService.updateInterviewNotes(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/medical")
	public ResponseEntity<UpdateResDto> updateMedical(@RequestBody MedicalCheckupUpdateReqDto data){
		final UpdateResDto response = progressStatusService.updateMedical(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/application-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllApplicationByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getApplicationByCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/application-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllApplicationByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getApplicationByCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/assessment-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllAssessmentByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getAssessmentByCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/assessment-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllAssessmentByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getAssessmentByCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/interview-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllInterviewByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getInterviewByCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/interview-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllInterviewByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getInterviewByCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@PostMapping("/hired-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllHiredByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getHiredByCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/hired-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllHiredByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getHiredByCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/offering-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllOfferingByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getOfferingByCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/offering-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllOfferingByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getOfferingByCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/medical-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllMedicalByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getMCUbyCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/medical-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllMedicalByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getMCUbyCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/reject-id")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllRejectByCandidate(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getRejectbyCandidate(data.getEmail(), data.getStartIndex(), data.getEndIndex());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/reject-id-non-pagination")
	public ResponseEntity<List<CandidateStageProcessResDto>> getAllRejectByCandidateNonPagination(
			@RequestBody JobCandidateStatusGetReqDto data){
		final List<CandidateStageProcessResDto> response = progressStatusService.getRejectbyCandidateNonPagination(data.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}	

package com.gencura.doctor.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gencura.appointment.entities.Appointment;
import com.gencura.common.utils.ApiResponse;
import com.gencura.doctor.dtos.DoctorResponse;
import com.gencura.doctor.dtos.DoctorSummaryResponse;
import com.gencura.doctor.dtos.UpdateDoctorProfileRequest;
import com.gencura.doctor.services.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/doctors")
@Validated
@RequiredArgsConstructor
public class DoctorController {
	
	private final DoctorService doctorService;
	
	// Self controllers
	@GetMapping("/me")
	@PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT_DOCTOR')")
	public ResponseEntity<ApiResponse<DoctorResponse>> getProfile(){
		return ResponseEntity.ok(doctorService.getMyProfile());
	}
	
	@PutMapping("/me")
	@PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT_DOCTOR')")
	public ResponseEntity<ApiResponse<DoctorResponse>> updateProfile(
			@Valid @RequestBody UpdateDoctorProfileRequest req){
		return ResponseEntity.ok(doctorService.updateMyProfile(req));
	}
	
	@PatchMapping("/me")
	@PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT_DOCTOR', 'RECEPTIONIST')")
	public ResponseEntity<ApiResponse<DoctorResponse>> updateAvailability(@RequestParam boolean isAvailable){
		return ResponseEntity.ok(doctorService.updateAvailability(isAvailable));
	}
	
	@GetMapping("/me/appointments")
	@PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT_DOCTOR')")
	public ResponseEntity<ApiResponse<List<Appointment>>> getSelfAppointments(){
		return ResponseEntity.ok(doctorService.getMyAppointments());
	}
	// ADMIN access controllers
	@GetMapping("/registration/{number}")
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorByRegistrationNumber(@PathVariable String req){
		return ResponseEntity.ok(doctorService.getDoctorByRegistrationNumber(req));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
	public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id){
		return ResponseEntity.ok(doctorService.deleteDoctor(id));
	}
	
	// open controllers with Auth
	@GetMapping("/")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<List<DoctorSummaryResponse>>> getAllDoctors(){
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable Long id){
		return ResponseEntity.ok(doctorService.getDoctorById(id));
	}
	
	@GetMapping("/specialization/{specialization}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<List<DoctorSummaryResponse>>> getDoctorsBySpelization(@PathVariable String specialization){
		return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
	}
	
	@GetMapping("/available")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<List<DoctorSummaryResponse>>> getAvailableDoctors(){
		return ResponseEntity.ok(doctorService.getAvailableDoctors());
	}
	
	@GetMapping("/experience/{years}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<List<DoctorSummaryResponse>>> getDoctorsWithExperience(@PathVariable int minYears){
		return ResponseEntity.ok(doctorService.getDoctorsByExperience(minYears));
	}
	
	@GetMapping("/{id}/appointments")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<List<Appointment>>> getAppointments(@PathVariable Long id){
		return ResponseEntity.ok(doctorService.getDoctorAppointments(id));
	}
	
}

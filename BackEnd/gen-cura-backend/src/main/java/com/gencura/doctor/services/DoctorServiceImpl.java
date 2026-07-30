package com.gencura.doctor.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gencura.appointment.entities.Appointment;
import com.gencura.common.exceptions.DuplicateResourceException;
import com.gencura.common.exceptions.InvalidOperationException;
import com.gencura.common.exceptions.ResourceNotFoundException;
import com.gencura.common.utils.ApiResponse;
import com.gencura.doctor.dtos.DoctorResponse;
import com.gencura.doctor.dtos.DoctorSummaryResponse;
import com.gencura.doctor.dtos.UpdateDoctorProfileRequest;
import com.gencura.doctor.entities.Doctor;
import com.gencura.doctor.repositories.DoctorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
	
	private final DoctorRepository doctorRepo;

	@Override
	public ApiResponse<DoctorResponse> getMyProfile() {
		
		Doctor doctor = doctorRepo.findByUserEmailAndUserActiveTrue(
					SecurityContextHolder.getContext()
					.getAuthentication().getName())
				.orElseThrow(()-> new ResourceNotFoundException("Data isn't available for Doctor"));
		
		return ApiResponse.success("Personal details found.", toDoctorResponse(doctor));
	}

	@Override
	public ApiResponse<DoctorResponse> updateMyProfile(UpdateDoctorProfileRequest req) {
		Doctor doctor = doctorRepo.findByUserEmailAndUserActiveTrue(
				SecurityContextHolder.getContext()
				.getAuthentication().getName())
			.orElseThrow(()-> new ResourceNotFoundException("Data isn't available for Doctor"));
		
		if(req.getSpecialization() != null && !req.getSpecialization().trim().isEmpty()) {
			doctor.setSpecialization(req.getSpecialization().trim());
		}
		if(req.getQualification() != null && !req.getQualification().trim().isEmpty()) {
			doctor.setQualification(req.getQualification().trim());
		}
		if(req.getExperienceYears() != null) {
			doctor.setExperienceYears(req.getExperienceYears());
		}
		if(req.getConsultationFees() != null) {
			doctor.setConsultationFee(BigDecimal.valueOf(req.getConsultationFees()));
		}
		if(req.getRegistrationNumber() != null && !req.getRegistrationNumber().trim().isEmpty()) {
			if (doctorRepo.existsByRegistrationNumber(req.getRegistrationNumber())
		            && !req.getRegistrationNumber().equals(doctor.getRegistrationNumber())) {

		        throw new DuplicateResourceException("Registration number already exists.");
		    }
			
			doctor.setRegistrationNumber(req.getRegistrationNumber().trim());
		}
		return ApiResponse.success("Details Updated.", toDoctorResponse(doc));
	}

	@Override
	public ApiResponse<DoctorResponse> updateAvailability(boolean req) {
		Doctor doctor = doctorRepo.findByUserEmailAndUserActiveTrue(
					SecurityContextHolder.getContext()
					.getAuthentication().getName())
				.orElseThrow(()-> new ResourceNotFoundException("Information isnt available"));
		
		if(doctor.isAvailable() == req) {
			throw new InvalidOperationException("Cannot Set same value to available.");
		}
		doctor.setAvailable(req);
		
		return ApiResponse.success("Availability status changed.", toDoctorResponse(doctor));
	}

	@Override
	public ApiResponse<List<Appointment>> getMyAppointments() {
		Doctor doctor = doctorRepo.findByUserEmailAndUserActiveTrue(
				SecurityContextHolder.getContext()
				.getAuthentication().getName())
			.orElseThrow(()-> new ResourceNotFoundException("Information isnt available"));
		// TODO: change getAppointments and List<Appointment> to toAppointmentResponse converter
		return ApiResponse.success("Found all appointments for doctor.", doctor.getAppointments());
	}

	@Override
	public ApiResponse<DoctorResponse> getDoctorById(Long doctorId) {
		Doctor doctor = doctorRepo.findById(doctorId)
				.orElseThrow(()-> new ResourceNotFoundException("Doctor with ID not available in DB"));
		
		return ApiResponse.success("Found Doctor with Id"+doctorId, toDoctorResponse(doctor));
	}

	@Override
	public ApiResponse<List<DoctorSummaryResponse>> getAllDoctors() {
		List<DoctorSummaryResponse> doctors = new ArrayList<>();
		for(Doctor d: doctorRepo.findAll()) {
			doctors.add(toDoctorSummaryResponse(d));
		}
		return ApiResponse.success("Found all Doctors.", doctors);
	}

	@Override
	public ApiResponse<List<DoctorSummaryResponse>> getAvailableDoctors() {
		List<DoctorSummaryResponse> doctors = new ArrayList<>();
		for(Doctor d: doctorRepo.findByIsAvailableTrue()) {
			doctors.add(toDoctorSummaryResponse(d));
		}
		return ApiResponse.success("Found available Doctors.", doctors);
	}

	@Override
	public ApiResponse<List<DoctorSummaryResponse>> getDoctorsBySpecialization(String specialization) {
		List<DoctorSummaryResponse> doctors = new ArrayList<>();
		for(Doctor d: doctorRepo.findAll()) {
			if(d.getSpecialization().contains(specialization)) {
				doctors.add(toDoctorSummaryResponse(d));
			}
		}
		return ApiResponse.success("Doctors with specializations found.", doctors);
	}

	@Override
	public ApiResponse<List<DoctorSummaryResponse>> getDoctorsByExperience(Integer years) {
		List<DoctorSummaryResponse> doctors = new ArrayList<>();
		for(Doctor d: doctorRepo.findByExperienceYearsGreaterThanEqual(years)) {
			doctors.add(toDoctorSummaryResponse(d));
		}
		return null;
	}

	@Override
	public ApiResponse<DoctorResponse> getDoctorByRegistrationNumber(String registrationNumber) {
		DoctorResponse doctor = toDoctorResponse(doctorRepo.findByRegistrationNumber(registrationNumber)
				.orElseThrow(()-> 
					new ResourceNotFoundException("Doctor is not available with Reg. number - "+registrationNumber)
				)
			);
		return ApiResponse.success("Doctor with given Reg. Number Found.", doctor);
	}

	@Override
	public ApiResponse<List<DoctorSummaryResponse>> getDoctorsByFeeRange(BigDecimal min, BigDecimal max) {
		List<DoctorSummaryResponse> doctors = new ArrayList<>();
		for(Doctor d: doctorRepo.findByConsultationFeeBetween(min, max)) {
			doctors.add(toDoctorSummaryResponse(d));
		}
		return ApiResponse.success(
				"Found Doctors with consultation fees between - "+min+" "+max, 
				doctors
			);
	}

	@Override
	public ApiResponse<Void> deleteDoctor(Long doctorId) {
		Doctor doctor = doctorRepo.findById(doctorId)
				.orElseThrow(()-> new ResourceNotFoundException("Doctor with ID not found."));
		
		doctor.getUser().setActive(false);
		
		return ApiResponse.success("Doctor deactivated", null);
	}

	@Override
	public ApiResponse<List<Appointment>> getDoctorAppointments(Long doctorId) {
		Doctor doctor = doctorRepo.findById(doctorId)
				.orElseThrow(()-> new ResourceNotFoundException("Requested doctor not found"));
		// TODO convert Appointment to appointmentResponse.
		return ApiResponse.success("Found all appointments belongs to doctor-ID"+doctorId, doctor.getAppointments());
	}

	private DoctorResponse toDoctorResponse(Doctor doctor) {
		return new DoctorResponse(
			doctor.getId(),
			doctor.getUser().getFullName(),
			doctor.getUser().getEmail(),
			doctor.getUser().getMobile(),
			doctor.getSpecialization(),
			doctor.getQualification(),
			doctor.getRegistrationNumber(),
			doctor.getConsultationFee(),
			doctor.getExperienceYears(),
			doctor.isAvailable()
		);
	}
	
	private DoctorSummaryResponse toDoctorSummaryResponse(Doctor doctor) {
		return new DoctorSummaryResponse(
			doctor.getId(),
			doctor.getUser().getFullName(),
			doctor.getSpecialization(),
			doctor.getConsultationFee(),
			doctor.isAvailable(),
			doctor.getExperienceYears()
		);
	}
}

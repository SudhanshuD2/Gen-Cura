package com.gencura.doctor.services;

import java.math.BigDecimal;
import java.util.List;

import com.gencura.appointment.entities.Appointment;
import com.gencura.common.utils.ApiResponse;
import com.gencura.doctor.dtos.DoctorResponse;
import com.gencura.doctor.dtos.DoctorSummaryResponse;
import com.gencura.doctor.dtos.UpdateDoctorProfileRequest;

public interface DoctorService {

	// Doctors self accessible methods - 
	ApiResponse<DoctorResponse> getMyProfile();

	ApiResponse<DoctorResponse> updateMyProfile(UpdateDoctorProfileRequest req);

	ApiResponse<DoctorResponse> updateAvailability(boolean req);
	
	ApiResponse<List<Appointment>> getMyAppointments();
	
	// ADMIN accessible methods - 
	ApiResponse<DoctorResponse> getDoctorById(Long doctorId);

	ApiResponse<List<DoctorSummaryResponse>> getAllDoctors();

	ApiResponse<List<DoctorSummaryResponse>> getAvailableDoctors();

	ApiResponse<List<DoctorSummaryResponse>> getDoctorsBySpecialization(String specialization);

	ApiResponse<List<DoctorSummaryResponse>> getDoctorsByExperience(Integer years);

	ApiResponse<DoctorResponse> getDoctorByRegistrationNumber(String registrationNumber);

	ApiResponse<List<DoctorSummaryResponse>> getDoctorsByFeeRange(BigDecimal min, BigDecimal max);
	
	// SUPER-ADMIN methods
	ApiResponse<Void> deleteDoctor(Long doctorId);
	
	// Shared method - 
	// TODO: update appointment to appointmentResponse after creating it
	ApiResponse<List<Appointment>> getDoctorAppointments(Long doctorId);
}

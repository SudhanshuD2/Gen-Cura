package com.gencura.doctor.dtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDoctorProfileRequest {
	private String specialization;
	
	private String qualification;
	
	private String registrationNumber;
	
	@PositiveOrZero
	private Double consultationFees;
	
	@PositiveOrZero
	private Integer experienceYears;
}

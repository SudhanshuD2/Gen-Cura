package com.gencura.doctor.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DoctorResponse {
	private Long doctorId;

	private String fullName;

	private String email;

	private String mobile;

	private String specialization;

	private String qualification;

	private String registrationNumber;

	private BigDecimal consultationFee;

	private Integer experienceYears;

	private boolean isAvailable;
}

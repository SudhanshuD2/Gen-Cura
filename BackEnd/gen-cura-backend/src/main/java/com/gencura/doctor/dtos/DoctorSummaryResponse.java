package com.gencura.doctor.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DoctorSummaryResponse {
	private Long doctorId;

	private String fullName;

	private String specialization;

	private BigDecimal consultationFee;

	private boolean isAvailable;
	
	private Integer experienceYears;
}

package com.gencura.doctor.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gencura.appointment.entities.Appointment;
import com.gencura.user.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="doctors")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Doctor {
	
	@Id
	@Column(name="doctor_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name="doctor_id", nullable = false)
	@ToString.Exclude
	private User user;
	
	@Column(length = 1000)
	private String specialization;
	
	private String qualification;
	
	@Column(name = "registration_number")
	private String registrationNumber;
	
	@Column(name = "consultation_fee", nullable = false)
	private BigDecimal consultationFee;
	
	@Column(name = "experience_years")
	private Integer experienceYears;
	
	@Column(name = "is_available")
	private boolean isAvailable = true;
	
	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appointments = new ArrayList<>();
}

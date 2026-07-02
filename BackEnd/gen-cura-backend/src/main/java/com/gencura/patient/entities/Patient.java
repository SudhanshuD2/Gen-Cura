package com.gencura.patient.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gencura.appointment.entities.Appointment;
import com.gencura.common.entities.BaseEntity;
import com.gencura.common.enums.BloodGroup;
import com.gencura.common.enums.Gender;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name="patient_id"))
public class Patient extends BaseEntity{
	
	@Column(name = "aadhaar_number", unique = true)
	private String aadhaarNumber;
	
	@Column(name = "full_name")
	private String fullName;
	
	
	private LocalDate dob;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender = Gender.NOT_SPECIFIED;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "blood_group")
	private BloodGroup bloodGroup;
	
	private String mobile;
	
	private String email;
	
	private String address;
	
	@Column(name = "emergency_person")
	private String emergencyContactName;
	
	@Column(name = "emergency_number")
	private String emergencyContactNumber;
	
	@Size(max = 1000)
	private String allergies;
	
	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointments = new ArrayList<>();
}

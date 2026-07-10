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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	
	@Column(name = "aadhaar_number", unique = true, length=12)
	private String aadhaarNumber;
	
	@Column(name = "full_name", length = 100)
	private String fullName;
	
	
	private LocalDate dob;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender = Gender.NOT_SPECIFIED;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "blood_group")
	private BloodGroup bloodGroup;
	
	@Column(length = 15)
	private String mobile;

	@Column(length = 150)
	private String email;

	@Column(length = 500)
	private String address;
	
	@Column(name = "emergency_person", length = 100)
	private String emergencyContactName;
	
	@Column(name = "emergency_number", length = 15)
	private String emergencyContactNumber;
	
	@Column(length = 1000)
	private String allergies;
	
	@OneToOne(mappedBy="patient")
	private AISummary summary;
	
	@OneToMany(mappedBy = "patient")
	@ToString.Exclude
	private List<Appointment> appointments = new ArrayList<>();
}

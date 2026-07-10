package com.gencura.appointment.entities;

import java.time.LocalDateTime;

import com.gencura.bill.entities.Bill;
import com.gencura.common.entities.BaseEntity;
import com.gencura.common.enums.AppointmentStatus;
import com.gencura.doctor.entities.Doctor;
import com.gencura.patient.entities.Patient;
import com.gencura.prescription.entities.Prescription;
import com.gencura.user.entities.User;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name="appointment_id"))
public class Appointment extends BaseEntity{
	
	@Column(name = "scheduled_at", nullable = false)
	private LocalDateTime scheduleAt;
	
	// GENERATED PER DAY. 
	@Column(name="token_number",nullable = false)
	private Integer tokenNumber;
	
	@Column(name = "chief_complaint", nullable = false, length=300)
	private String chiefComplaint;
	
	@Column(name = "appointment_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private AppointmentStatus appointmentStatus = AppointmentStatus.BOOKED;
	
	@Column(length = 500)
	private String remarks;
	
	// Relational
	
	@ManyToOne
	@JoinColumn(name="booked_by", nullable = false)
	private User bookedBy;	
	
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;
	
	@OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
	private Prescription prescription;
	
	@OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
	private Consultation consultation;
	
	@OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
	private Bill bill;
}

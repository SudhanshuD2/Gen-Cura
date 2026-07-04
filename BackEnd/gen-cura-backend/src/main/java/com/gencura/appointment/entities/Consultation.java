package com.gencura.appointment.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="consultations")
@Getter
@Setter
@NoArgsConstructor
public class Consultation {
	
	@MapsId
	@OneToOne
	@JoinColumn(name="appointment_id", nullable = false)
	private Appointment appointment;
	
	@Size(max=1000)
	private String diagnosis;
	
	@Size(max=500)
	private String advice;
	
	/*
	 * clinical notes will be shared to 
	 * model to build users general health.
	 * as doctors can have it for next treatments,
	 * AI models table will be shared with users with
	 * OneToOne relations. 
	 */
	@Column(name = "clinical_notes")
	@Size(max=500)
	private String clinicalNotes;
	
	@Column(name = "followup_date")
	private LocalDate followupDate;
}

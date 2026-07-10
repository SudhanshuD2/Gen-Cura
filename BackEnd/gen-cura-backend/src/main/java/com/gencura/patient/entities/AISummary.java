package com.gencura.patient.entities;

import com.gencura.common.entities.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ai_summary")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name="summary_id"))
public class AISummary extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@Column(length = 1000)
	private String summary;
		
}

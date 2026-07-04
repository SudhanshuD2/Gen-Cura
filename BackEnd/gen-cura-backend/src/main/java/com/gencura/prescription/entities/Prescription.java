package com.gencura.prescription.entities;

import java.util.ArrayList;
import java.util.List;

import com.gencura.appointment.entities.Appointment;
import com.gencura.common.utils.PrescriptionItemListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
public class Prescription {
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "appointment_id", nullable = false)
	private Appointment appointment;
	
	@Column(columnDefinition = "json")
    @Convert(converter = PrescriptionItemListConverter.class)
    private List<PrescriptionItem> items = new ArrayList<>();
}

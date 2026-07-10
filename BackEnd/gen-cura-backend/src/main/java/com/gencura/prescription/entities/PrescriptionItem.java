package com.gencura.prescription.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PrescriptionItem{
	
	private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;
    private Integer quantity;
}

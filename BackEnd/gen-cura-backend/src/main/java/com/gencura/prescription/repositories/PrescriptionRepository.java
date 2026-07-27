package com.gencura.prescription.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.prescription.entities.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}

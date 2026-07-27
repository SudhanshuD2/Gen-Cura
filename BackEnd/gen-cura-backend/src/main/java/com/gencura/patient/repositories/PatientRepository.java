package com.gencura.patient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.patient.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}

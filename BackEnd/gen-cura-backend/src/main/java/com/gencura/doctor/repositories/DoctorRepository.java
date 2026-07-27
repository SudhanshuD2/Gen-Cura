package com.gencura.doctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.doctor.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}

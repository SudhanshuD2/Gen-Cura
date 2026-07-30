package com.gencura.doctor.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.doctor.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	public Optional<Doctor> findByRegistrationNumber(String regNumber);
	
	public Optional<Doctor> findByUserEmailAndUserActiveTrue(String email);
	
	public boolean existsByRegistrationNumber(String registrationNumber);
	
	public List<Doctor> findByIsAvailableTrue();
	
	public List<Doctor> findByIsAvailable(boolean available);
	
	public List<Doctor> findByExperienceYearsGreaterThanEqual(Integer years);
	
	public List<Doctor> findByQualificationContainingIgnoreCase(String qualification);
	
	public List<Doctor> findByConsultationFeeBetween(BigDecimal min, BigDecimal max);
	
		
}

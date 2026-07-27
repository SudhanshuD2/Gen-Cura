package com.gencura.appointment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.appointment.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}

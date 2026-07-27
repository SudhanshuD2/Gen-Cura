package com.gencura.catlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.catlog.entities.RateCardEntry;

public interface RateCardEntryRepository extends JpaRepository<RateCardEntry, Long> {

}

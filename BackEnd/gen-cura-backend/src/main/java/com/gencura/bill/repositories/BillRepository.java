package com.gencura.bill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gencura.bill.entities.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}

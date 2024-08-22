package com.namrata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.namrata.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
}
package com.namrata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.namrata.model.BillingDetails;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
}
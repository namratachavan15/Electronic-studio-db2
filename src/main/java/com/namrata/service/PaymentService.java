package com.namrata.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	public boolean processPayment(String firstName, String lastName, String address, String email, double amount) {
        // Call payment gateway API or service here
        // Simulate success/failure for demo purposes
        boolean paymentSuccess = true; // Replace with actual logic

        return paymentSuccess;
    }
}

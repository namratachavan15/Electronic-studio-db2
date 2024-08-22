package com.namrata.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.namrata.global.GlobalData;
import com.namrata.model.BillingDetails;
import com.namrata.repository.BillingDetailsRepository;
import com.namrata.repository.PaymentRepository;
import com.namrata.service.PaymentService;

@Controller
public class PaymentController {

	 @Autowired
	  BillingDetailsRepository billingDetailsRepository;
	 @Autowired
	 PaymentService paymentService;
	 @PostMapping("/payNow")
	    public String processPayment(Model model,HttpSession session,
	                                 @RequestParam String firstName,
	                                 @RequestParam String lastName,
	                                 @RequestParam String address,
	                                 @RequestParam String email,
	                                 @RequestParam Double total) {

	        if (total == null) {
	            // Handle null total value
	            model.addAttribute("error", "Total amount cannot be null.");
	            return "failure";
	        }

	       

	        BillingDetails billingDetails=new BillingDetails();
	        
	        billingDetails.setFirstName(firstName);
	        billingDetails.setLastName(lastName);
	        billingDetails.setAddress(address);
	        billingDetails.setEmail(email);
	        billingDetails.setTotal(total);

	        billingDetailsRepository.save(billingDetails);
	        boolean paymentSuccess = paymentService.processPayment(firstName, lastName, address, email, total);
	        if (paymentSuccess) {
	        	GlobalData.cart.clear();
	        	session.setAttribute("cartCount", 0);
	            return "redirect:/";
	        } else {
	            return "failure";
	        }
	    }
}

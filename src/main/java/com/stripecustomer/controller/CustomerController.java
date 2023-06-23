package com.stripecustomer.controller;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;

import com.stripecustomer.payload.CustomerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private static final String STRIPE_SECRET_KEY = "sk_test_51NGQTISCtDQyaIYYd4UmnfPjMXllQgVZt4dTHEumts4e4ZFjAs4wJFveLt1eNERaAP73tCVTFZKGyuea9CGprWEC00f1GWOZ28";

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Stripe.apiKey = STRIPE_SECRET_KEY;

        CustomerCreateParams params = CustomerCreateParams.builder()
                .setDescription(customerRequest.getDescription())
                .setName(customerRequest.getName())
                .setEmail(customerRequest.getEmail())
                .build();


        try {
            Customer customer = Customer.create(params);
            // Assuming you want to save the customer's ID, name, and email in your database
            saveCustomerInfo(customer.getId(), customerRequest.getName(), customerRequest.getEmail());
            return ResponseEntity.ok("Customer created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create customer");
        }
    }

    private void saveCustomerInfo(String customerId, String name, String email) {
        // Save the customer information (customerId, name, email) in your database or perform any necessary actions
        // Example code:
        // YourDatabase.saveCustomer(customerId, name, email);
    }
}


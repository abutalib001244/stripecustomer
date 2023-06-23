package com.stripecustomer.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripecustomer.payload.CustomerRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final String STRIPE_SECRET_KEY = "sk_test_51NGQTISCtDQyaIYYd4UmnfPjMXllQgVZt4dTHEumts4e4ZFjAs4wJFveLt1eNERaAP73tCVTFZKGyuea9CGprWEC00f1GWOZ28";

    public String createCustomer(CustomerRequest customerRequest) {
        Stripe.apiKey = STRIPE_SECRET_KEY;

        CustomerCreateParams params = CustomerCreateParams.builder()
                .setDescription(customerRequest.getDescription())
                .setName(customerRequest.getName())
                .setEmail(customerRequest.getEmail())
                .build();


        try {
            Customer customer = Customer.create(params);
            saveCustomerInfo(customer.getId(), customerRequest.getName(), customerRequest.getEmail());
            return customer.getId();
        } catch (StripeException e) {
            // Handle Stripe-specific exceptions
            throw new RuntimeException("Failed to create customer in Stripe");
        }
    }

    private void saveCustomerInfo(String customerId, String name, String email) {
        // Save the customer information (customerId, name, email) in your database or perform any necessary actions
        // Example code:
        // YourDatabase.saveCustomer(customerId, name, email);
    }
}

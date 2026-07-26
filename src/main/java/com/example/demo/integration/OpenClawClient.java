package com.example.demo.integration;

import org.springframework.stereotype.Component;

@Component
public class OpenClawClient {

    public String scheduleMaintenance(Long leaseId) {

        return "Maintenance and inspection scheduled successfully for Lease ID : "
                + leaseId;
    }
}
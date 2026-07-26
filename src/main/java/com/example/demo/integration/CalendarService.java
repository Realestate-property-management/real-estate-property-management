package com.example.demo.integration;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    public String scheduleInspection(Long inspectionId) {

        return "Inspection scheduled in external calendar successfully. Inspection ID : "
                + inspectionId;

    }

}
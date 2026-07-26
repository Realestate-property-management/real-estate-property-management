package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.integration.CalendarService;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/schedule/{inspectionId}")
    public String scheduleInspection(
            @PathVariable Long inspectionId) {

        return calendarService.scheduleInspection(inspectionId);

    }

}
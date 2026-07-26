package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.integration.OpenClawClient;

@RestController
@RequestMapping("/api/openclaw")
@CrossOrigin(origins = "*")
public class OpenClawController {

    @Autowired
    private OpenClawClient openClawService;

    @PostMapping("/schedule/{leaseId}")
    public String scheduleMaintenance(@PathVariable Long leaseId) {

        return openClawService.scheduleMaintenance(leaseId);

    }

}
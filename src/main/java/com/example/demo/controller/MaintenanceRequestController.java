package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.MaintenanceRequest;
import com.example.demo.service.MaintenanceRequestService;

@RestController
@RequestMapping("/api/maintenance-requests")
@CrossOrigin(origins = "*")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;

    @PostMapping
    public MaintenanceRequest saveMaintenanceRequest(@RequestBody MaintenanceRequest request) {
        return maintenanceRequestService.saveMaintenanceRequest(request);
    }

    @GetMapping
    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        return maintenanceRequestService.getAllMaintenanceRequests();
    }

    @GetMapping("/{id}")
    public MaintenanceRequest getMaintenanceRequestById(@PathVariable Long id) {
        return maintenanceRequestService.getMaintenanceRequestById(id);
    }

    @PutMapping("/{id}")
    public MaintenanceRequest updateMaintenanceRequest(@PathVariable Long id,
                                                       @RequestBody MaintenanceRequest request) {
        return maintenanceRequestService.updateMaintenanceRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteMaintenanceRequest(@PathVariable Long id) {
        maintenanceRequestService.deleteMaintenanceRequest(id);
        return "Maintenance Request deleted successfully.";
    }

  
    @PatchMapping("/{id}/status")
    public MaintenanceRequest updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return maintenanceRequestService.updateStatus(id, status);
    }
}
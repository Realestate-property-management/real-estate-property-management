package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Lease;
import com.example.demo.service.LeaseService;

@RestController
@RequestMapping("/api/leases")
@CrossOrigin(origins = "*")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @PostMapping
    public Lease saveLease(@RequestBody Lease lease) {
        return leaseService.saveLease(lease);
    }

    @GetMapping
    public List<Lease> getAllLeases() {
        return leaseService.getAllLeases();
    }

    @GetMapping("/{id}")
    public Lease getLeaseById(@PathVariable Long id) {
        return leaseService.getLeaseById(id);
    }

    @PutMapping("/{id}")
    public Lease updateLease(@PathVariable Long id,
                             @RequestBody Lease lease) {
        return leaseService.updateLease(id, lease);
    }

    @DeleteMapping("/{id}")
    public String deleteLease(@PathVariable Long id) {
        leaseService.deleteLease(id);
        return "Lease deleted successfully.";
    }
}
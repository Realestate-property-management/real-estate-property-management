package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.example.demo.models.Contractor;
import com.example.demo.service.ContractorService;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "*")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

    // Create Contractor
    @PostMapping
    public Contractor saveContractor(@Valid @RequestBody Contractor contractor) {
        return contractorService.saveContractor(contractor);
    }

    // Get All Contractors
    @GetMapping
    public List<Contractor> getAllContractors() {
        return contractorService.getAllContractors();
    }

    // Get Contractor By ID
    @GetMapping("/{id}")
    public Contractor getContractorById(@PathVariable Long id) {
        return contractorService.getContractorById(id);
    }

    // Update Contractor
    @PutMapping("/{id}")
    public Contractor updateContractor(@PathVariable Long id,
                                       @Valid @RequestBody Contractor contractor) {
        return contractorService.updateContractor(id, contractor);
    }

    // Delete Contractor
    @DeleteMapping("/{id}")
    public String deleteContractor(@PathVariable Long id) {
        contractorService.deleteContractor(id);
        return "Contractor deleted successfully.";
    }
}
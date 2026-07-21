package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Contractor;
import com.example.demo.service.ContractorService;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "*")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

    @PostMapping
    public Contractor saveContractor(@RequestBody Contractor contractor) {
        return contractorService.saveContractor(contractor);
    }

    @GetMapping
    public List<Contractor> getAllContractors() {
        return contractorService.getAllContractors();
    }

    @GetMapping("/{id}")
    public Contractor getContractorById(@PathVariable Long id) {
        return contractorService.getContractorById(id);
    }

    @PutMapping("/{id}")
    public Contractor updateContractor(@PathVariable Long id,
                                       @RequestBody Contractor contractor) {
        return contractorService.updateContractor(id, contractor);
    }

    @DeleteMapping("/{id}")
    public String deleteContractor(@PathVariable Long id) {
        contractorService.deleteContractor(id);
        return "Contractor deleted successfully.";
    }
}
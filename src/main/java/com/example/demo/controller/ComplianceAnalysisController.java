package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.ComplianceAnalysis;
import com.example.demo.service.ComplianceAnalysisService;

@RestController
@RequestMapping("/api/compliance-analyses")
@CrossOrigin(origins = "*")
public class ComplianceAnalysisController {

    @Autowired
    private ComplianceAnalysisService complianceAnalysisService;

    @PostMapping
    public ComplianceAnalysis saveComplianceAnalysis(@RequestBody ComplianceAnalysis analysis) {
        return complianceAnalysisService.saveComplianceAnalysis(analysis);
    }

    @GetMapping
    public List<ComplianceAnalysis> getAllComplianceAnalyses() {
        return complianceAnalysisService.getAllComplianceAnalyses();
    }

    @GetMapping("/{id}")
    public ComplianceAnalysis getComplianceAnalysisById(@PathVariable Long id) {
        return complianceAnalysisService.getComplianceAnalysisById(id);
    }

    @PutMapping("/{id}")
    public ComplianceAnalysis updateComplianceAnalysis(@PathVariable Long id,
                                                       @RequestBody ComplianceAnalysis analysis) {
        return complianceAnalysisService.updateComplianceAnalysis(id, analysis);
    }

    @DeleteMapping("/{id}")
    public String deleteComplianceAnalysis(@PathVariable Long id) {
        complianceAnalysisService.deleteComplianceAnalysis(id);
        return "Compliance Analysis deleted successfully.";
    }
}
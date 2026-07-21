package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.ComplianceIssue;
import com.example.demo.service.ComplianceIssueService;

@RestController
@RequestMapping("/api/compliance-issues")
@CrossOrigin(origins = "*")
public class ComplianceIssueController {

    @Autowired
    private ComplianceIssueService complianceIssueService;

    @PostMapping
    public ComplianceIssue saveComplianceIssue(@RequestBody ComplianceIssue issue) {
        return complianceIssueService.saveComplianceIssue(issue);
    }

    @GetMapping
    public List<ComplianceIssue> getAllComplianceIssues() {
        return complianceIssueService.getAllComplianceIssues();
    }

    @GetMapping("/{id}")
    public ComplianceIssue getComplianceIssueById(@PathVariable Long id) {
        return complianceIssueService.getComplianceIssueById(id);
    }

    @PutMapping("/{id}")
    public ComplianceIssue updateComplianceIssue(@PathVariable Long id,
                                                 @RequestBody ComplianceIssue issue) {
        return complianceIssueService.updateComplianceIssue(id, issue);
    }

    @DeleteMapping("/{id}")
    public String deleteComplianceIssue(@PathVariable Long id) {
        complianceIssueService.deleteComplianceIssue(id);
        return "Compliance Issue deleted successfully.";
    }
}
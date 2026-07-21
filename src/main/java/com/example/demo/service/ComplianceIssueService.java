package com.example.demo.service;

import java.util.List;
import com.example.demo.models.ComplianceIssue;

public interface ComplianceIssueService {

    ComplianceIssue saveComplianceIssue(ComplianceIssue issue);

    ComplianceIssue updateComplianceIssue(Long id, ComplianceIssue issue);

    void deleteComplianceIssue(Long id);

    ComplianceIssue getComplianceIssueById(Long id);

    List<ComplianceIssue> getAllComplianceIssues();

}
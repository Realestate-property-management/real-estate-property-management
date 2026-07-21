package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ComplianceIssue;
import com.example.demo.repository.ComplianceIssueRepository;
import com.example.demo.service.ComplianceIssueService;

@Service
public class ComplianceIssueServiceImpl implements ComplianceIssueService {

    @Autowired
    private ComplianceIssueRepository complianceIssueRepository;

    @Override
    public ComplianceIssue saveComplianceIssue(ComplianceIssue issue) {
        return complianceIssueRepository.save(issue);
    }

    @Override
    public ComplianceIssue updateComplianceIssue(Long id, ComplianceIssue issue) {

        ComplianceIssue existingIssue = complianceIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compliance Issue not found with ID: " + id));

        existingIssue.setAnalysis(issue.getAnalysis());
        existingIssue.setLease(issue.getLease());
        existingIssue.setIssueType(issue.getIssueType());
        existingIssue.setCategory(issue.getCategory());
        existingIssue.setClauseText(issue.getClauseText());
        existingIssue.setAiExplanation(issue.getAiExplanation());
        existingIssue.setRecommendation(issue.getRecommendation());
        existingIssue.setRiskLevel(issue.getRiskLevel());
        existingIssue.setPageNumber(issue.getPageNumber());
        existingIssue.setDeadlineDate(issue.getDeadlineDate());
        existingIssue.setReviewStatus(issue.getReviewStatus());
        existingIssue.setReviewedBy(issue.getReviewedBy());
        existingIssue.setReviewedAt(issue.getReviewedAt());
        existingIssue.setCreatedAt(issue.getCreatedAt());

        return complianceIssueRepository.save(existingIssue);
    }

    @Override
    public void deleteComplianceIssue(Long id) {

        ComplianceIssue existingIssue = complianceIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compliance Issue not found with ID: " + id));

        complianceIssueRepository.delete(existingIssue);
    }

    @Override
    public ComplianceIssue getComplianceIssueById(Long id) {

        return complianceIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compliance Issue not found with ID: " + id));
    }

    @Override
    public List<ComplianceIssue> getAllComplianceIssues() {

        return complianceIssueRepository.findAll();
    }
}
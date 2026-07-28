package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ComplianceAnalysis;
import com.example.demo.repository.ComplianceAnalysisRepository;
import com.example.demo.service.ComplianceAnalysisService;

@Service
public class ComplianceAnalysisServiceImpl implements ComplianceAnalysisService {

    private static final String NOT_FOUND_MSG = "Compliance Analysis not found with ID: ";

    @Autowired
    private ComplianceAnalysisRepository complianceAnalysisRepository;

    @Override
    public ComplianceAnalysis saveComplianceAnalysis(ComplianceAnalysis analysis) {
        return complianceAnalysisRepository.save(analysis);
    }

    @Override
    public ComplianceAnalysis updateComplianceAnalysis(Long id, ComplianceAnalysis analysis) {

        ComplianceAnalysis existingAnalysis = complianceAnalysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MSG + id));

        existingAnalysis.setLease(analysis.getLease());
        existingAnalysis.setDocument(analysis.getDocument());
        existingAnalysis.setModelName(analysis.getModelName());
        existingAnalysis.setComplianceScore(analysis.getComplianceScore());
        existingAnalysis.setOverallRisk(analysis.getOverallRisk());
        existingAnalysis.setAnalysisSummary(analysis.getAnalysisSummary());
        existingAnalysis.setStatus(analysis.getStatus());
        existingAnalysis.setStartedAt(analysis.getStartedAt());
        existingAnalysis.setCompletedAt(analysis.getCompletedAt());
        existingAnalysis.setCreatedAt(analysis.getCreatedAt());

        return complianceAnalysisRepository.save(existingAnalysis);
    }

    @Override
    public void deleteComplianceAnalysis(Long id) {

        ComplianceAnalysis existingAnalysis = complianceAnalysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MSG + id));

        complianceAnalysisRepository.delete(existingAnalysis);
    }

    @Override
    public ComplianceAnalysis getComplianceAnalysisById(Long id) {

        return complianceAnalysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_MSG + id));
    }

    @Override
    public List<ComplianceAnalysis> getAllComplianceAnalyses() {

        return complianceAnalysisRepository.findAll();
    }
}
package com.example.demo.service;

import java.util.List;
import com.example.demo.models.ComplianceAnalysis;

public interface ComplianceAnalysisService {

    ComplianceAnalysis saveComplianceAnalysis(ComplianceAnalysis analysis);

    ComplianceAnalysis updateComplianceAnalysis(Long id, ComplianceAnalysis analysis);

    void deleteComplianceAnalysis(Long id);

    ComplianceAnalysis getComplianceAnalysisById(Long id);

    List<ComplianceAnalysis> getAllComplianceAnalyses();

}
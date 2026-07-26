package com.example.demo.dto;

public class ComplianceResponse {

    private Long leaseId;
    private String analysisResult;
    private String riskLevel;
    private String status;

    public ComplianceResponse() {
    }

    public ComplianceResponse(Long leaseId, String analysisResult,
                              String riskLevel, String status) {
        this.leaseId = leaseId;
        this.analysisResult = analysisResult;
        this.riskLevel = riskLevel;
        this.status = status;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
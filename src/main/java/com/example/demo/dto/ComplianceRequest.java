package com.example.demo.dto;

public class ComplianceRequest {

    private Long leaseId;
    private String extractedText;

    public ComplianceRequest() {
    }

    public ComplianceRequest(Long leaseId, String extractedText) {
        this.leaseId = leaseId;
        this.extractedText = extractedText;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }
}
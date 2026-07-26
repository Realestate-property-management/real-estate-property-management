package com.example.demo.dto;

public class LangChainRequest {

    private Long leaseId;
    private String s3FileUrl;

    public LangChainRequest() {
    }

    public LangChainRequest(Long leaseId, String s3FileUrl) {
        this.leaseId = leaseId;
        this.s3FileUrl = s3FileUrl;
    }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public String getS3FileUrl() {
        return s3FileUrl;
    }

    public void setS3FileUrl(String s3FileUrl) {
        this.s3FileUrl = s3FileUrl;
    }
}
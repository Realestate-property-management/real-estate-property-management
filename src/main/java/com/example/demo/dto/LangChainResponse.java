package com.example.demo.dto;

import java.util.List;

public class LangChainResponse {

    private String extracted_text;

    private List<String> text_chunks;

    private Analysis analysis;

    private List<MaintenanceJob> maintenance_jobs;

    public LangChainResponse() {
    }

    public String getExtracted_text() {
        return extracted_text;
    }

    public void setExtracted_text(String extracted_text) {
        this.extracted_text = extracted_text;
    }

    public List<String> getText_chunks() {
        return text_chunks;
    }

    public void setText_chunks(List<String> text_chunks) {
        this.text_chunks = text_chunks;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public List<MaintenanceJob> getMaintenance_jobs() {
        return maintenance_jobs;
    }

    public void setMaintenance_jobs(List<MaintenanceJob> maintenance_jobs) {
        this.maintenance_jobs = maintenance_jobs;
    }

}
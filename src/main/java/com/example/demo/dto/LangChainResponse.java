package com.example.demo.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LangChainResponse {

    @JsonProperty("extracted_text")
    private String extractedText;

    @JsonProperty("text_chunks")
    private List<String> textChunks;

    private Analysis analysis;

    @JsonProperty("maintenance_jobs")
    private List<MaintenanceJob> maintenanceJobs;

    public LangChainResponse() {
        // Empty constructor for serialization
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public List<String> getTextChunks() {
        return textChunks;
    }

    public void setTextChunks(List<String> textChunks) {
        this.textChunks = textChunks;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public List<MaintenanceJob> getMaintenanceJobs() {
        return maintenanceJobs;
    }

    public void setMaintenanceJobs(List<MaintenanceJob> maintenanceJobs) {
        this.maintenanceJobs = maintenanceJobs;
    }

}
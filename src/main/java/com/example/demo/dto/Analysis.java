package com.example.demo.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Analysis {

    private String risk;

    @JsonProperty("non_standard_clauses")
    private List<String> nonStandardClauses;

    @JsonProperty("compliance_issues")
    private List<String> complianceIssues;

    @JsonProperty("maintenance_deadlines")
    private List<MaintenanceDeadline> maintenanceDeadlines;

    public Analysis() {
        // Empty constructor for serialization
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public List<String> getNonStandardClauses() {
        return nonStandardClauses;
    }

    public void setNonStandardClauses(List<String> nonStandardClauses) {
        this.nonStandardClauses = nonStandardClauses;
    }

    public List<String> getComplianceIssues() {
        return complianceIssues;
    }

    public void setComplianceIssues(List<String> complianceIssues) {
        this.complianceIssues = complianceIssues;
    }

    public List<MaintenanceDeadline> getMaintenanceDeadlines() {
        return maintenanceDeadlines;
    }

    public void setMaintenanceDeadlines(List<MaintenanceDeadline> maintenanceDeadlines) {
        this.maintenanceDeadlines = maintenanceDeadlines;
    }
}
package com.example.demo.dto;

import java.util.List;

public class Analysis {

    private String risk;

    private List<String> non_standard_clauses;

    private List<String> compliance_issues;

    private List<MaintenanceDeadline> maintenance_deadlines;

    public Analysis() {}

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public List<String> getNon_standard_clauses() {
        return non_standard_clauses;
    }

    public void setNon_standard_clauses(List<String> non_standard_clauses) {
        this.non_standard_clauses = non_standard_clauses;
    }

    public List<String> getCompliance_issues() {
        return compliance_issues;
    }

    public void setCompliance_issues(List<String> compliance_issues) {
        this.compliance_issues = compliance_issues;
    }

    public List<MaintenanceDeadline> getMaintenance_deadlines() {
        return maintenance_deadlines;
    }

    public void setMaintenance_deadlines(List<MaintenanceDeadline> maintenance_deadlines) {
        this.maintenance_deadlines = maintenance_deadlines;
    }
}
package com.example.demo.dto;

public class DashboardResponse {

    private long totalProperties;
    private long totalTenants;
    private long activeLeases;
    private long pendingMaintenance;
    private long upcomingInspections;
    private long complianceIssues;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalProperties, long totalTenants,
                             long activeLeases, long pendingMaintenance,
                             long upcomingInspections, long complianceIssues) {
        this.totalProperties = totalProperties;
        this.totalTenants = totalTenants;
        this.activeLeases = activeLeases;
        this.pendingMaintenance = pendingMaintenance;
        this.upcomingInspections = upcomingInspections;
        this.complianceIssues = complianceIssues;
    }

    public long getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(long totalProperties) {
        this.totalProperties = totalProperties;
    }

    public long getTotalTenants() {
        return totalTenants;
    }

    public void setTotalTenants(long totalTenants) {
        this.totalTenants = totalTenants;
    }

    public long getActiveLeases() {
        return activeLeases;
    }

    public void setActiveLeases(long activeLeases) {
        this.activeLeases = activeLeases;
    }

    public long getPendingMaintenance() {
        return pendingMaintenance;
    }

    public void setPendingMaintenance(long pendingMaintenance) {
        this.pendingMaintenance = pendingMaintenance;
    }

    public long getUpcomingInspections() {
        return upcomingInspections;
    }

    public void setUpcomingInspections(long upcomingInspections) {
        this.upcomingInspections = upcomingInspections;
    }

    public long getComplianceIssues() {
        return complianceIssues;
    }

    public void setComplianceIssues(long complianceIssues) {
        this.complianceIssues = complianceIssues;
    }
}
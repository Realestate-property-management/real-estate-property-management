package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leases")
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lease_id")
    private Long leaseId;

    @NotBlank(message = "Lease number is required")
    @Size(max = 50, message = "Lease number cannot exceed 50 characters")
    @Column(name = "lease_number", unique = true, nullable = false)
    private String leaseNumber;

    @NotNull(message = "Property is required")
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @NotNull(message = "Tenant is required")
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @NotBlank(message = "Unit number is required")
    @Size(max = 20, message = "Unit number cannot exceed 20 characters")
    @Column(name = "unit_number")
    private String unitNumber;

    @NotNull(message = "Lease start date is required")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "Lease end date is required")
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull(message = "Monthly rent is required")
    @Positive(message = "Monthly rent must be greater than zero")
    @Column(name = "monthly_rent")
    private Double monthlyRent;

    @NotNull(message = "Security deposit is required")
    @Min(value = 0, message = "Security deposit cannot be negative")
    @Column(name = "security_deposit")
    private Double securityDeposit;

    @NotBlank(message = "Lease status is required")
    @Column(name = "lease_status")
    private String leaseStatus;

    @NotBlank(message = "Analysis status is required")
    @Column(name = "analysis_status")
    private String analysisStatus;

    @Size(max = 1000, message = "Lease document URL cannot exceed 1000 characters")
    @Column(name = "lease_document_url", length = 1000)
    private String leaseDocumentUrl;

    @NotNull(message = "Created By user is required")
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default Constructor
    public Lease() {
    }

    // Getters and Setters

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    public String getLeaseNumber() {
        return leaseNumber;
    }

    public void setLeaseNumber(String leaseNumber) {
        this.leaseNumber = leaseNumber;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public String getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(String leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    public String getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(String analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public String getLeaseDocumentUrl() {
        return leaseDocumentUrl;
    }

    public void setLeaseDocumentUrl(String leaseDocumentUrl) {
        this.leaseDocumentUrl = leaseDocumentUrl;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
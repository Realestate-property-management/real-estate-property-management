package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_issues")
public class ComplianceIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long issueId;

    @ManyToOne
    @JoinColumn(name = "analysis_id")
    private ComplianceAnalysis analysis;

    @ManyToOne
    @JoinColumn(name = "lease_id")
    private Lease lease;

    @Column(name = "issue_type")
    private String issueType;

    @Column(name = "category")
    private String category;

    @Column(name = "clause_text", columnDefinition = "TEXT")
    private String clauseText;

    @Column(name = "ai_explanation", columnDefinition = "TEXT")
    private String aiExplanation;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @Column(name = "review_status")
    private String reviewStatus;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default Constructor
    public ComplianceIssue() {
    }

    // Parameterized Constructor
    public ComplianceIssue(Long issueId,
                           ComplianceAnalysis analysis,
                           Lease lease,
                           String issueType,
                           String category,
                           String clauseText,
                           String aiExplanation,
                           String recommendation,
                           String riskLevel,
                           Integer pageNumber,
                           LocalDate deadlineDate,
                           String reviewStatus,
                           User reviewedBy,
                           LocalDateTime reviewedAt,
                           LocalDateTime createdAt) {

        this.issueId = issueId;
        this.analysis = analysis;
        this.lease = lease;
        this.issueType = issueType;
        this.category = category;
        this.clauseText = clauseText;
        this.aiExplanation = aiExplanation;
        this.recommendation = recommendation;
        this.riskLevel = riskLevel;
        this.pageNumber = pageNumber;
        this.deadlineDate = deadlineDate;
        this.reviewStatus = reviewStatus;
        this.reviewedBy = reviewedBy;
        this.reviewedAt = reviewedAt;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public ComplianceAnalysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(ComplianceAnalysis analysis) {
        this.analysis = analysis;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClauseText() {
        return clauseText;
    }

    public void setClauseText(String clauseText) {
        this.clauseText = clauseText;
    }

    public String getAiExplanation() {
        return aiExplanation;
    }

    public void setAiExplanation(String aiExplanation) {
        this.aiExplanation = aiExplanation;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
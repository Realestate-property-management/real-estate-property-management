package com.example.demo.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.integration.AiAnalysisClient;
import com.example.demo.integration.OpenClawClient;
import com.example.demo.integration.S3Service;
import com.example.demo.models.Notification;

import com.example.demo.models.ComplianceAnalysis;
import com.example.demo.models.Inspection;
import com.example.demo.models.AuditLog;
import com.example.demo.models.Lease;

import com.example.demo.repository.LeaseRepository;

import com.example.demo.service.AuditLogService;
import com.example.demo.service.ComplianceAnalysisService;
import com.example.demo.service.InspectionService;
import com.example.demo.service.LeaseService;
import com.example.demo.service.NotificationService;

@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private S3Service s3Service;
    
    @Autowired
    private AiAnalysisClient aiAnalysisClient;

    @Autowired
    private ComplianceAnalysisService complianceAnalysisService;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private OpenClawClient openClawClient;

    @Override
    public Lease saveLease(Lease lease) {
        return leaseRepository.save(lease);
    }

    @Override
    public Lease updateLease(Long id, Lease lease) {

        Lease existingLease = leaseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Lease not found with ID: " + id));

        existingLease.setLeaseNumber(lease.getLeaseNumber());
        existingLease.setProperty(lease.getProperty());
        existingLease.setTenant(lease.getTenant());
        existingLease.setUnitNumber(lease.getUnitNumber());
        existingLease.setStartDate(lease.getStartDate());
        existingLease.setEndDate(lease.getEndDate());
        existingLease.setMonthlyRent(lease.getMonthlyRent());
        existingLease.setSecurityDeposit(lease.getSecurityDeposit());
        existingLease.setLeaseStatus(lease.getLeaseStatus());
        existingLease.setAnalysisStatus(lease.getAnalysisStatus());
        existingLease.setLeaseDocumentUrl(lease.getLeaseDocumentUrl());
        existingLease.setCreatedBy(lease.getCreatedBy());
        existingLease.setUpdatedAt(lease.getUpdatedAt());

        return leaseRepository.save(existingLease);
    }

    @Override
    public void deleteLease(Long id) {

        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Lease not found with ID: " + id));

        // Delete lease document from Amazon S3
        if (lease.getLeaseDocumentUrl() != null &&
                !lease.getLeaseDocumentUrl().isBlank()) {

            String fileName = extractFileName(lease.getLeaseDocumentUrl());
            s3Service.deleteFile(fileName);
        }

        leaseRepository.delete(lease);
    }

    @Override
    public Lease getLeaseById(Long id) {

        return leaseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Lease not found with ID: " + id));
    }

    @Override
    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    // Upload Lease PDF to Amazon S3
    @Override
    public Lease uploadLeaseDocument(Long leaseId, MultipartFile file) {

        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() ->
                        new RuntimeException("Lease not found with ID: " + leaseId));

     // Upload PDF to Amazon S3
        String fileUrl = s3Service.uploadFile(file);

        lease.setLeaseDocumentUrl(fileUrl);

        lease = leaseRepository.save(lease);

        /*
         * STEP 1
         * Send uploaded lease to AI service
         */

        String fileName = extractFileName(fileUrl);

        // Create S3 URI expected by FastAPI
        String bucketName = fileUrl
                .replace("https://", "")
                .split("\\.")[0];

        String s3Uri = "s3://" + bucketName + "/" + fileName;

     // ===============================
     // Call FastAPI
     // ===============================

     System.out.println("=================================");
     System.out.println("File URL   : " + fileUrl);
     System.out.println("Bucket     : " + bucketName);
     System.out.println("File Name  : " + fileName);
     System.out.println("S3 URI     : " + s3Uri);
     System.out.println("=================================");

     Map<String, Object> aiResponse;

     try {

         aiResponse = aiAnalysisClient.analyzeLease(s3Uri);

         System.out.println("AI Response = " + aiResponse);

     } catch (Exception ex) {

         // Update lease status as FAILED
         lease.setAnalysisStatus("FAILED");
         lease.setUpdatedAt(LocalDateTime.now());

         leaseRepository.save(lease);

         ex.printStackTrace();

         throw new RuntimeException(
                 "AI lease analysis failed: " + ex.getMessage(),
                 ex
         );
     }
  // AI analysis completed successfully
     lease.setAnalysisStatus("COMPLETED");
     lease.setUpdatedAt(LocalDateTime.now());

     lease = leaseRepository.save(lease);
        
        /*
         * STEP 3
         * Save Compliance Analysis
         */

        Map<String, Object> analysisMap =
                (Map<String, Object>) aiResponse.get("analysis");

        ComplianceAnalysis analysis = new ComplianceAnalysis();

        analysis.setLease(lease);
        analysis.setModelName("Nous Hermes");

        // If the AI returns these fields, save them.
        if (analysisMap != null) {

            if (analysisMap.get("compliance_score") != null) {
                analysis.setComplianceScore(
                        Double.valueOf(analysisMap.get("compliance_score").toString()));
            }

            if (analysisMap.get("overall_risk") != null) {
                analysis.setOverallRisk(
                        analysisMap.get("overall_risk").toString());
            }

            if (analysisMap.get("analysis_summary") != null) {
                analysis.setAnalysisSummary(
                        analysisMap.get("analysis_summary").toString());
            }
        }

        analysis.setStatus("COMPLETED");
        analysis.setStartedAt(LocalDateTime.now());
        analysis.setCompletedAt(LocalDateTime.now());
        analysis.setCreatedAt(LocalDateTime.now());
        
        complianceAnalysisService.saveComplianceAnalysis(analysis);

        /*
         * STEP 4
         * Create Inspection
         */

        Inspection inspection = new Inspection();

        inspection.setProperty(lease.getProperty());

        inspection.setInspectionType("STRUCTURAL");

        inspection.setTitle("AI Generated Property Inspection");

        inspection.setDescription(
                "Inspection automatically created from AI lease analysis.");

        inspection.setStatus("PENDING");

        inspection.setPriority("HIGH");

        inspection.setSourceType("AI");

        inspection.setCreatedAt(LocalDateTime.now());

        inspection.setUpdatedAt(LocalDateTime.now());

        inspectionService.saveInspection(inspection);

        /*
         * STEP 5
         * Notification
         */

        Notification notification = new Notification();

        notification.setUser(lease.getCreatedBy());
        notification.setTitle("AI Lease Analysis Completed");
        notification.setMessage(
                "Lease analysis completed and inspection has been scheduled.");
        notification.setNotificationType("SYSTEM");
        notification.setReferenceType("LEASE");
        notification.setReferenceId(lease.getLeaseId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationService.saveNotification(notification);
        /*
         * STEP 6
         * Audit Log
         */
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(lease.getCreatedBy());
        auditLog.setAction("LEASE_ANALYZED");
        auditLog.setEntityType("LEASE");
        auditLog.setEntityId(lease.getLeaseId());
        auditLog.setDescription(
                "Lease uploaded, analyzed by AI, compliance analysis saved, and inspection created.");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogService.saveAuditLog(auditLog);

        /*
         * STEP 7
         * OpenClaw Scheduling
         */

        openClawClient.scheduleMaintenance(lease.getLeaseId());
        return lease;
    }

    // Download Lease PDF from Amazon S3
    @Override
    public byte[] downloadLeaseDocument(Long leaseId) {

        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() ->
                        new RuntimeException("Lease not found with ID: " + leaseId));

        if (lease.getLeaseDocumentUrl() == null ||
                lease.getLeaseDocumentUrl().isBlank()) {
            throw new RuntimeException("No lease document uploaded.");
        }

        String fileName = extractFileName(lease.getLeaseDocumentUrl());

        return s3Service.downloadFile(fileName);
    }

    // Delete Lease PDF from Amazon S3
    @Override
    public void deleteLeaseDocument(Long leaseId) {

        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() ->
                        new RuntimeException("Lease not found with ID: " + leaseId));

        if (lease.getLeaseDocumentUrl() == null ||
                lease.getLeaseDocumentUrl().isBlank()) {
            throw new RuntimeException("No lease document uploaded.");
        }

        String fileName = extractFileName(lease.getLeaseDocumentUrl());

        s3Service.deleteFile(fileName);

        lease.setLeaseDocumentUrl(null);

        leaseRepository.save(lease);
    }

    // Helper Method
    private String extractFileName(String fileUrl) {

        int index = fileUrl.lastIndexOf('/');

        if (index == -1) {
            throw new RuntimeException("Invalid S3 URL");
        }

        return fileUrl.substring(index + 1);
    }
}
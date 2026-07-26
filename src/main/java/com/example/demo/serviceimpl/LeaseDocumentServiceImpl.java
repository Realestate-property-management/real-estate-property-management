package com.example.demo.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.integration.S3Service;
import com.example.demo.models.Lease;
import com.example.demo.models.LeaseDocument;
import com.example.demo.models.User;
import com.example.demo.repository.LeaseDocumentRepository;
import com.example.demo.models.ComplianceAnalysis;
import com.example.demo.models.ComplianceIssue;
import com.example.demo.repository.ComplianceIssueRepository;
import com.example.demo.repository.ComplianceAnalysisRepository;
import com.example.demo.models.MaintenanceRequest;
import com.example.demo.repository.MaintenanceRequestRepository;
import com.example.demo.repository.LeaseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.models.Notification;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.models.AuditLog;
import com.example.demo.repository.AuditLogRepository;
import com.example.demo.service.LeaseDocumentService;
import com.example.demo.integration.AiAnalysisClient;
import java.util.Map;

@Service
public class LeaseDocumentServiceImpl implements LeaseDocumentService {

    @Autowired
    private LeaseDocumentRepository leaseDocumentRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;
    
    @Autowired
    private AiAnalysisClient aiAnalysisClient;
    @Autowired
    private ComplianceAnalysisRepository complianceAnalysisRepository;
    @Autowired
    private ComplianceIssueRepository complianceIssueRepository;
    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Override
    public LeaseDocument saveLeaseDocument(LeaseDocument document) {
        return leaseDocumentRepository.save(document);
    }

    @Override
    public LeaseDocument updateLeaseDocument(Long id, LeaseDocument document) {

        LeaseDocument existingDocument = leaseDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Document not found with ID: " + id));

        existingDocument.setLease(document.getLease());
        existingDocument.setOriginalFileName(document.getOriginalFileName());
        existingDocument.setStoredFileName(document.getStoredFileName());
        existingDocument.setS3Bucket(document.getS3Bucket());
        existingDocument.setS3ObjectKey(document.getS3ObjectKey());
        existingDocument.setFileSize(document.getFileSize());
        existingDocument.setContentType(document.getContentType());
        existingDocument.setUploadStatus(document.getUploadStatus());
        existingDocument.setUploadedBy(document.getUploadedBy());
        existingDocument.setUploadedAt(document.getUploadedAt());

        return leaseDocumentRepository.save(existingDocument);
    }

    @Override
    public void deleteLeaseDocument(Long id) {

        LeaseDocument existingDocument = leaseDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Document not found with ID: " + id));

        leaseDocumentRepository.delete(existingDocument);
    }

    @Override
    public LeaseDocument getLeaseDocumentById(Long id) {

        return leaseDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Document not found with ID: " + id));
    }

    @Override
    public List<LeaseDocument> getAllLeaseDocuments() {

        return leaseDocumentRepository.findAll();
    }

    // ==============================
    // Upload Lease Document to S3
    // ==============================
    @Override
    public LeaseDocument uploadLeaseDocument(Long leaseId,
                                             Long userId,
                                             MultipartFile file) {

        // Upload file to Amazon S3
        s3Service.uploadFile(file);

        // Get Lease
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        // Get User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create LeaseDocument
        LeaseDocument document = new LeaseDocument();

        document.setLease(lease);
        document.setUploadedBy(user);

        document.setOriginalFileName(file.getOriginalFilename());
        document.setStoredFileName(file.getOriginalFilename());

        document.setS3Bucket("propaudit-lease-documents");
        document.setS3ObjectKey(file.getOriginalFilename());

        document.setFileSize(file.getSize());
        document.setContentType(file.getContentType());

        document.setUploadStatus("UPLOADED");
        document.setUploadedAt(LocalDateTime.now());

        // Save LeaseDocument
        LeaseDocument savedDocument = leaseDocumentRepository.save(document);
        AuditLog uploadLog = new AuditLog();

        uploadLog.setUser(user);
        uploadLog.setAction("DOCUMENT_UPLOADED");
        uploadLog.setEntityType("LEASE_DOCUMENT");
        uploadLog.setEntityId(savedDocument.getDocumentId());
        uploadLog.setDescription("Lease document uploaded successfully.");
        uploadLog.setIpAddress("127.0.0.1");
        uploadLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(uploadLog);
        Notification uploadNotification = new Notification();

        uploadNotification.setUser(user);
        uploadNotification.setTitle("Lease Uploaded");
        uploadNotification.setMessage("Lease document uploaded successfully.");
        uploadNotification.setNotificationType("UPLOAD");
        uploadNotification.setReferenceType("LEASE_DOCUMENT");
        uploadNotification.setReferenceId(savedDocument.getDocumentId());
        uploadNotification.setIsRead(false);
        uploadNotification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(uploadNotification);

        // Build S3 URI
        String s3Uri =
                "s3://"
                + savedDocument.getS3Bucket()
                + "/"
                + savedDocument.getS3ObjectKey();
        AuditLog startLog = new AuditLog();

        startLog.setUser(user);
        startLog.setAction("AI_ANALYSIS_STARTED");
        startLog.setEntityType("LEASE");
        startLog.setEntityId(lease.getLeaseId());
        startLog.setDescription("AI analysis started.");
        startLog.setIpAddress("127.0.0.1");
        startLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(startLog);

        // Call AI Service
        try {

            Map<String, Object> aiResponse =
                    aiAnalysisClient.analyzeLease(s3Uri);
            ComplianceAnalysis analysis = new ComplianceAnalysis();

            analysis.setLease(lease);

            analysis.setDocument(savedDocument);

            // You can change this later if you use another model
            analysis.setModelName("Gemini");

            // Compliance score can be calculated later
            analysis.setComplianceScore(0.0);

            // Read risk from AI response
            Map<String, Object> analysisMap =
                    (Map<String, Object>) aiResponse.get("analysis");
            
            System.out.println("========== AI ANALYSIS ==========");
            System.out.println(aiResponse);
            System.out.println("================================");

            if (analysisMap != null && analysisMap.get("risk") != null) {
                analysis.setOverallRisk(analysisMap.get("risk").toString());
            } else {
                analysis.setOverallRisk("UNKNOWN");
            }

            // Save complete AI response as summary
            analysis.setAnalysisSummary(aiResponse.toString());

            analysis.setStatus("COMPLETED");

            analysis.setStartedAt(LocalDateTime.now());

            analysis.setCompletedAt(LocalDateTime.now());

            analysis.setCreatedAt(LocalDateTime.now());

            complianceAnalysisRepository.save(analysis);
            Map<String, Object> analysisData =
                    (Map<String, Object>) aiResponse.get("analysis");

            List<String> complianceIssues =
                    (List<String>) analysisData.get("compliance_issues");

            List<String> clauses =
                    (List<String>) analysisData.get("non_standard_clauses");

            if (complianceIssues != null) {

                for (int i = 0; i < complianceIssues.size(); i++) {

                    ComplianceIssue issue = new ComplianceIssue();

                    issue.setAnalysis(analysis);

                    issue.setLease(lease);

                    issue.setIssueType("AI_DETECTED");

                    issue.setCategory("LEASE");

                    if (clauses != null && i < clauses.size()) {
                        issue.setClauseText(clauses.get(i));
                    }

                    issue.setAiExplanation(complianceIssues.get(i));

                    issue.setRecommendation("Review this clause.");

                    issue.setRiskLevel(analysis.getOverallRisk());

                    issue.setReviewStatus("PENDING");

                    issue.setCreatedAt(LocalDateTime.now());

                    complianceIssueRepository.save(issue);
                }
            }
            
            List<Map<String, Object>> maintenanceDeadlines =
                    (List<Map<String, Object>>) analysisData.get("maintenance_deadlines");

            if (maintenanceDeadlines != null) {

                for (Map<String, Object> deadline : maintenanceDeadlines) {

                    MaintenanceRequest request = new MaintenanceRequest();

                    request.setProperty(lease.getProperty());

                    request.setTenant(lease.getTenant());

                    request.setTitle("AI Generated Maintenance");

                    request.setDescription(deadline.get("description").toString());

                    request.setCategory("LEASE");

                    request.setPriority(deadline.get("priority").toString());

                    request.setStatus("OPEN");

                    request.setCreatedBy(user);

                    maintenanceRequestRepository.save(request);
                    Notification maintenanceNotification = new Notification();

                    maintenanceNotification.setUser(user);
                    maintenanceNotification.setTitle("Maintenance Request Created");
                    maintenanceNotification.setMessage(request.getDescription());
                    maintenanceNotification.setNotificationType("MAINTENANCE");
                    maintenanceNotification.setReferenceType("MAINTENANCE_REQUEST");
                    maintenanceNotification.setReferenceId(request.getRequestId());
                    maintenanceNotification.setIsRead(false);
                    maintenanceNotification.setCreatedAt(LocalDateTime.now());

                    notificationRepository.save(maintenanceNotification);
                }
            }
         // Update Lease Analysis Status
            lease.setAnalysisStatus("COMPLETED");
            leaseRepository.save(lease);
            AuditLog completedLog = new AuditLog();

            completedLog.setUser(user);
            completedLog.setAction("AI_ANALYSIS_COMPLETED");
            completedLog.setEntityType("LEASE");
            completedLog.setEntityId(lease.getLeaseId());
            completedLog.setDescription("AI analysis completed successfully.");
            completedLog.setIpAddress("127.0.0.1");
            completedLog.setCreatedAt(LocalDateTime.now());

            auditLogRepository.save(completedLog);
            Notification completedNotification = new Notification();

            completedNotification.setUser(user);
            completedNotification.setTitle("AI Analysis Completed");
            completedNotification.setMessage("Lease analysis completed successfully.");
            completedNotification.setNotificationType("AI_ANALYSIS");
            completedNotification.setReferenceType("LEASE");
            completedNotification.setReferenceId(lease.getLeaseId());
            completedNotification.setIsRead(false);
            completedNotification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(completedNotification);

        } catch (Exception e) {

            // Update Lease Analysis Status
            lease.setAnalysisStatus("FAILED");
            leaseRepository.save(lease);
            AuditLog failedLog = new AuditLog();

            failedLog.setUser(user);
            failedLog.setAction("AI_ANALYSIS_FAILED");
            failedLog.setEntityType("LEASE");
            failedLog.setEntityId(lease.getLeaseId());   // ✅ Correct
            failedLog.setDescription(e.getMessage());
            failedLog.setIpAddress("127.0.0.1");
            failedLog.setCreatedAt(LocalDateTime.now());

            auditLogRepository.save(failedLog);
            Notification failedNotification = new Notification();

            failedNotification.setUser(user);
            failedNotification.setTitle("AI Analysis Failed");
            failedNotification.setMessage(e.getMessage());
            failedNotification.setNotificationType("ERROR");
            failedNotification.setReferenceType("LEASE");
            failedNotification.setReferenceId(lease.getLeaseId());
            failedNotification.setIsRead(false);
            failedNotification.setCreatedAt(LocalDateTime.now());

            notificationRepository.save(failedNotification);

            System.out.println("========== AI ANALYSIS FAILED ==========");
            e.printStackTrace();
            System.out.println("========================================");
        }

        return savedDocument;
    }
}
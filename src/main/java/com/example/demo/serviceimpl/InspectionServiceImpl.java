package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Inspection;
import com.example.demo.repository.InspectionRepository;
import com.example.demo.service.InspectionService;
import java.time.LocalDateTime;

import com.example.demo.models.AuditLog;
import com.example.demo.models.Notification;
import com.example.demo.models.User;

import com.example.demo.repository.AuditLogRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
@Service
public class InspectionServiceImpl implements InspectionService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String INSPECTION_TYPE = "INSPECTION";
    private static final String INSPECTION_PREFIX = "Inspection ";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String INSPECTION_NOT_FOUND_MSG = "Inspection not found with ID: ";

    @Autowired
    private InspectionRepository inspectionRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Inspection saveInspection(Inspection inspection) {

        inspection.setCreatedAt(LocalDateTime.now());
        inspection.setUpdatedAt(LocalDateTime.now());

        // Save inspection
        Inspection savedInspection = inspectionRepository.save(inspection);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("INSPECTION_CREATED");
        auditLog.setEntityType(INSPECTION_TYPE);
        auditLog.setEntityId(savedInspection.getInspectionId());
        auditLog.setDescription(INSPECTION_PREFIX + savedInspection.getTitle() + " created.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Inspection Scheduled");
        notification.setMessage(savedInspection.getTitle() + " has been scheduled.");
        notification.setNotificationType(INSPECTION_TYPE);
        notification.setReferenceType(INSPECTION_TYPE);
        notification.setReferenceId(savedInspection.getInspectionId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedInspection;
    }

    @Override
    public Inspection updateInspection(Long id, Inspection inspection) {

        Inspection existingInspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(INSPECTION_NOT_FOUND_MSG + id));

        existingInspection.setProperty(inspection.getProperty());
        existingInspection.setInspectionType(inspection.getInspectionType());
        existingInspection.setTitle(inspection.getTitle());
        existingInspection.setDescription(inspection.getDescription());
        existingInspection.setScheduledDate(inspection.getScheduledDate());
        existingInspection.setDueDate(inspection.getDueDate());
        existingInspection.setStatus(inspection.getStatus());
        existingInspection.setPriority(inspection.getPriority());
        existingInspection.setContractor(inspection.getContractor());
        existingInspection.setSourceType(inspection.getSourceType());
        existingInspection.setExternalEventId(inspection.getExternalEventId());
        existingInspection.setNotes(inspection.getNotes());
        existingInspection.setCreatedAt(inspection.getCreatedAt());
        existingInspection.setUpdatedAt(LocalDateTime.now());
        
        // Save updated inspection
        Inspection updatedInspection = inspectionRepository.save(existingInspection);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("INSPECTION_UPDATED");
        auditLog.setEntityType(INSPECTION_TYPE);
        auditLog.setEntityId(updatedInspection.getInspectionId());
        auditLog.setDescription(INSPECTION_PREFIX + updatedInspection.getTitle() + " updated.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Inspection Updated");
        notification.setMessage(updatedInspection.getTitle() + " updated successfully.");
        notification.setNotificationType(INSPECTION_TYPE);
        notification.setReferenceType(INSPECTION_TYPE);
        notification.setReferenceId(updatedInspection.getInspectionId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedInspection;  
    }

    @Override
    public void deleteInspection(Long id) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(INSPECTION_NOT_FOUND_MSG + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        String inspectionTitle = inspection.getTitle();

        inspectionRepository.delete(inspection);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("INSPECTION_DELETED");
        auditLog.setEntityType(INSPECTION_TYPE);
        auditLog.setEntityId(id);
        auditLog.setDescription(INSPECTION_PREFIX + inspectionTitle + " deleted.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Inspection Deleted");
        notification.setMessage(inspectionTitle + " has been deleted.");
        notification.setNotificationType(INSPECTION_TYPE);
        notification.setReferenceType(INSPECTION_TYPE);
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public Inspection getInspectionById(Long id) {

        return inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(INSPECTION_NOT_FOUND_MSG + id));
    }

    @Override
    public List<Inspection> getAllInspections() {

        return inspectionRepository.findAll();
    }

    @Override
    public Inspection updateStatus(Long id, String status) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(INSPECTION_NOT_FOUND_MSG + id));

        inspection.setStatus(status);

        return inspectionRepository.save(inspection);
    }
}
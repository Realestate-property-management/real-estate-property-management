package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.MaintenanceRequest;
import com.example.demo.repository.MaintenanceRequestRepository;
import com.example.demo.service.MaintenanceRequestService;
import java.time.LocalDateTime;

import com.example.demo.models.AuditLog;
import com.example.demo.models.Notification;
import com.example.demo.models.User;

import com.example.demo.repository.AuditLogRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String REQUEST_TYPE = "MAINTENANCE_REQUEST";
    private static final String REQ_PREFIX = "Maintenance request ";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String MAIN_TYPE = "MAINTENANCE";
    private static final String NOT_FOUND_MSG = "Maintenance Request not found with ID: ";

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest request) {

        MaintenanceRequest savedRequest = maintenanceRequestRepository.save(request);

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // Audit Log
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("MAINTENANCE_CREATED");
        auditLog.setEntityType(REQUEST_TYPE);
        auditLog.setEntityId(savedRequest.getRequestId());
        auditLog.setDescription(REQ_PREFIX + savedRequest.getTitle() + " created.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // Notification
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Maintenance Request Created");
        notification.setMessage(savedRequest.getTitle() + " has been created.");
        notification.setNotificationType(MAIN_TYPE);
        notification.setReferenceType(REQUEST_TYPE);
        notification.setReferenceId(savedRequest.getRequestId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedRequest;
    }
    @Override
    public MaintenanceRequest updateMaintenanceRequest(Long id, MaintenanceRequest request) {

        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        NOT_FOUND_MSG + id));

        existingRequest.setProperty(request.getProperty());
        existingRequest.setTenant(request.getTenant());
        existingRequest.setTitle(request.getTitle());
        existingRequest.setDescription(request.getDescription());
        existingRequest.setCategory(request.getCategory());
        existingRequest.setPriority(request.getPriority());
        existingRequest.setStatus(request.getStatus());
        existingRequest.setContractor(request.getContractor());
        existingRequest.setScheduledDate(request.getScheduledDate());
        existingRequest.setCompletedDate(request.getCompletedDate());
        existingRequest.setEstimatedCost(request.getEstimatedCost());
        existingRequest.setActualCost(request.getActualCost());
        existingRequest.setCreatedBy(request.getCreatedBy());
        // Save updated request
        MaintenanceRequest updatedRequest = maintenanceRequestRepository.save(existingRequest);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("MAINTENANCE_UPDATED");
        auditLog.setEntityType(REQUEST_TYPE);
        auditLog.setEntityId(updatedRequest.getRequestId());
        auditLog.setDescription(REQ_PREFIX + updatedRequest.getTitle() + " updated.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Maintenance Request Updated");
        notification.setMessage(updatedRequest.getTitle() + " updated successfully.");
        notification.setNotificationType(MAIN_TYPE);
        notification.setReferenceType(REQUEST_TYPE);
        notification.setReferenceId(updatedRequest.getRequestId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedRequest;

    }

    @Override
    public void deleteMaintenanceRequest(Long id) {

        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        NOT_FOUND_MSG + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        String title = existingRequest.getTitle();

        maintenanceRequestRepository.delete(existingRequest);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("MAINTENANCE_DELETED");
        auditLog.setEntityType(REQUEST_TYPE);
        auditLog.setEntityId(id);
        auditLog.setDescription(REQ_PREFIX + title + " deleted.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Maintenance Request Deleted");
        notification.setMessage(title + " has been deleted.");
        notification.setNotificationType(MAIN_TYPE);
        notification.setReferenceType(REQUEST_TYPE);
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public MaintenanceRequest getMaintenanceRequestById(Long id) {

        return maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        NOT_FOUND_MSG + id));
    }

    @Override
    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        return maintenanceRequestRepository.findAll();
    }

    @Override
    public MaintenanceRequest updateStatus(Long id, String status) {

        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        NOT_FOUND_MSG + id));

        existingRequest.setStatus(status);

        // Save updated status
        MaintenanceRequest updatedRequest = maintenanceRequestRepository.save(existingRequest);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("MAINTENANCE_STATUS_UPDATED");
        auditLog.setEntityType(REQUEST_TYPE);
        auditLog.setEntityId(updatedRequest.getRequestId());
        auditLog.setDescription("Maintenance request status changed to " + status + ".");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Maintenance Status Updated");
        notification.setMessage(updatedRequest.getTitle() + " status changed to " + status + ".");
        notification.setNotificationType(MAIN_TYPE);
        notification.setReferenceType(REQUEST_TYPE);
        notification.setReferenceId(updatedRequest.getRequestId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedRequest;
    }
}
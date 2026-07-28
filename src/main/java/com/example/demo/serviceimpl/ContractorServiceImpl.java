package com.example.demo.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Contractor;
import com.example.demo.repository.ContractorRepository;
import com.example.demo.service.ContractorService;

import com.example.demo.models.AuditLog;
import com.example.demo.models.Notification;
import com.example.demo.models.User;

import com.example.demo.repository.AuditLogRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
@Service
public class ContractorServiceImpl implements ContractorService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String CONTRACTOR_TYPE = "CONTRACTOR";
    private static final String CONTRACTOR_PREFIX = "Contractor ";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String CONTRACTOR_NOT_FOUND_MSG = "Contractor not found with ID: ";

    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Contractor saveContractor(Contractor contractor) {

        contractor.setCreatedAt(LocalDateTime.now());
        contractor.setUpdatedAt(LocalDateTime.now());

        // Save contractor first
        Contractor savedContractor = contractorRepository.save(contractor);

        // Temporary user (replace later with logged-in user)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("CONTRACTOR_CREATED");
        auditLog.setEntityType(CONTRACTOR_TYPE);
        auditLog.setEntityId(savedContractor.getContractorId());
        auditLog.setDescription(CONTRACTOR_PREFIX + savedContractor.getCompanyName() + " created.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Contractor Added");
        notification.setMessage(savedContractor.getCompanyName() + " has been added successfully.");
        notification.setNotificationType(CONTRACTOR_TYPE);
        notification.setReferenceType(CONTRACTOR_TYPE);
        notification.setReferenceId(savedContractor.getContractorId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedContractor;
    }

    @Override
    public Contractor updateContractor(Long id, Contractor contractor) {

        Contractor existingContractor = contractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CONTRACTOR_NOT_FOUND_MSG + id));

        existingContractor.setCompanyName(contractor.getCompanyName());
        existingContractor.setContactPerson(contractor.getContactPerson());
        existingContractor.setEmail(contractor.getEmail());
        existingContractor.setPhone(contractor.getPhone());
        existingContractor.setServiceType(contractor.getServiceType());
        existingContractor.setAddress(contractor.getAddress());
        existingContractor.setRating(contractor.getRating());
        existingContractor.setStatus(contractor.getStatus());

        existingContractor.setUpdatedAt(LocalDateTime.now());

        // Save updated contractor
        Contractor updatedContractor = contractorRepository.save(existingContractor);

        // Temporary user (replace later with logged-in user)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("CONTRACTOR_UPDATED");
        auditLog.setEntityType(CONTRACTOR_TYPE);
        auditLog.setEntityId(updatedContractor.getContractorId());
        auditLog.setDescription(CONTRACTOR_PREFIX + updatedContractor.getCompanyName() + " updated.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Contractor Updated");
        notification.setMessage(updatedContractor.getCompanyName() + " updated successfully.");
        notification.setNotificationType(CONTRACTOR_TYPE);
        notification.setReferenceType(CONTRACTOR_TYPE);
        notification.setReferenceId(updatedContractor.getContractorId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedContractor;
    }

    @Override
    public void deleteContractor(Long id) {

        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CONTRACTOR_NOT_FOUND_MSG + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        String companyName = contractor.getCompanyName();

        contractorRepository.delete(contractor);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("CONTRACTOR_DELETED");
        auditLog.setEntityType(CONTRACTOR_TYPE);
        auditLog.setEntityId(id);
        auditLog.setDescription(CONTRACTOR_PREFIX + companyName + " deleted.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Contractor Deleted");
        notification.setMessage(companyName + " has been removed.");
        notification.setNotificationType(CONTRACTOR_TYPE);
        notification.setReferenceType(CONTRACTOR_TYPE);
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public Contractor getContractorById(Long id) {

        return contractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CONTRACTOR_NOT_FOUND_MSG + id));
    }

    @Override
    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }
}
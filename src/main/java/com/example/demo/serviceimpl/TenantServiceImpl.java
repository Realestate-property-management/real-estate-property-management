package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Tenant;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.TenantService;
import java.time.LocalDateTime;

import com.example.demo.models.AuditLog;
import com.example.demo.models.Notification;
import com.example.demo.models.User;

import com.example.demo.repository.AuditLogRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
@Service
public class TenantServiceImpl implements TenantService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String TENANT_TYPE = "TENANT";
    private static final String TENANT_PREFIX = "Tenant ";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String TENANT_NOT_FOUND_MSG = "Tenant not found with ID: ";

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Tenant saveTenant(Tenant tenant) {

        tenant.setCreatedAt(LocalDateTime.now());
        tenant.setUpdatedAt(LocalDateTime.now());

        // Save tenant
        Tenant savedTenant = tenantRepository.save(tenant);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("TENANT_CREATED");
        auditLog.setEntityType(TENANT_TYPE);
        auditLog.setEntityId(savedTenant.getTenantId());
        auditLog.setDescription(TENANT_PREFIX + savedTenant.getFirstName() + " " + savedTenant.getLastName() + " created.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Tenant Added");
        notification.setMessage(savedTenant.getFirstName() + " " + savedTenant.getLastName() + " has been added.");
        notification.setNotificationType(TENANT_TYPE);
        notification.setReferenceType(TENANT_TYPE);
        notification.setReferenceId(savedTenant.getTenantId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedTenant;
    }

    @Override
    public Tenant updateTenant(Long id, Tenant tenant) {

        Tenant existingTenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TENANT_NOT_FOUND_MSG + id));

        existingTenant.setFirstName(tenant.getFirstName());
        existingTenant.setLastName(tenant.getLastName());
        existingTenant.setEmail(tenant.getEmail());
        existingTenant.setPhone(tenant.getPhone());
        existingTenant.setDateOfBirth(tenant.getDateOfBirth());
        existingTenant.setEmergencyContactName(tenant.getEmergencyContactName());
        existingTenant.setEmergencyContactPhone(tenant.getEmergencyContactPhone());
        existingTenant.setStatus(tenant.getStatus());
        // Update timestamp
        existingTenant.setUpdatedAt(LocalDateTime.now());

        // Save updated tenant
        Tenant updatedTenant = tenantRepository.save(existingTenant);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("TENANT_UPDATED");
        auditLog.setEntityType(TENANT_TYPE);
        auditLog.setEntityId(updatedTenant.getTenantId());
        auditLog.setDescription(TENANT_PREFIX + updatedTenant.getFirstName() + " " + updatedTenant.getLastName() + " updated.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Tenant Updated");
        notification.setMessage(updatedTenant.getFirstName() + " " + updatedTenant.getLastName() + " updated successfully.");
        notification.setNotificationType(TENANT_TYPE);
        notification.setReferenceType(TENANT_TYPE);
        notification.setReferenceId(updatedTenant.getTenantId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedTenant;
    }

    @Override
    public void deleteTenant(Long id) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TENANT_NOT_FOUND_MSG + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        String tenantName = tenant.getFirstName() + " " + tenant.getLastName();

        // Delete tenant
        tenantRepository.delete(tenant);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("TENANT_DELETED");
        auditLog.setEntityType(TENANT_TYPE);
        auditLog.setEntityId(id);
        auditLog.setDescription(TENANT_PREFIX + tenantName + " deleted.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Tenant Deleted");
        notification.setMessage(tenantName + " has been removed.");
        notification.setNotificationType(TENANT_TYPE);
        notification.setReferenceType(TENANT_TYPE);
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public Tenant getTenantById(Long id) {

        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TENANT_NOT_FOUND_MSG + id));
    }

    @Override
    public List<Tenant> getAllTenants() {

        return tenantRepository.findAll();
    }

}
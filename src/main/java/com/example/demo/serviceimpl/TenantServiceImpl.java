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
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("TENANT_CREATED");
        auditLog.setEntityType("TENANT");
        auditLog.setEntityId(savedTenant.getTenantId());
        auditLog.setDescription("Tenant " + savedTenant.getFirstName() + " " + savedTenant.getLastName() + " created.");
        auditLog.setIpAddress("127.0.0.1");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Tenant Added");
        notification.setMessage(savedTenant.getFirstName() + " " + savedTenant.getLastName() + " has been added.");
        notification.setNotificationType("TENANT");
        notification.setReferenceType("TENANT");
        notification.setReferenceId(savedTenant.getTenantId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedTenant;
    }

    @Override
    public Tenant updateTenant(Long id, Tenant tenant) {

        Tenant existingTenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("TENANT_UPDATED");
        auditLog.setEntityType("TENANT");
        auditLog.setEntityId(updatedTenant.getTenantId());
        auditLog.setDescription("Tenant " + updatedTenant.getFirstName() + " " + updatedTenant.getLastName() + " updated.");
        auditLog.setIpAddress("127.0.0.1");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Tenant Updated");
        notification.setMessage(updatedTenant.getFirstName() + " " + updatedTenant.getLastName() + " updated successfully.");
        notification.setNotificationType("TENANT");
        notification.setReferenceType("TENANT");
        notification.setReferenceId(updatedTenant.getTenantId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedTenant;
    }

    @Override
    public void deleteTenant(Long id) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String tenantName = tenant.getFirstName() + " " + tenant.getLastName();

        // Delete tenant
        tenantRepository.delete(tenant);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("TENANT_DELETED");
        auditLog.setEntityType("TENANT");
        auditLog.setEntityId(id);
        auditLog.setDescription("Tenant " + tenantName + " deleted.");
        auditLog.setIpAddress("127.0.0.1");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Tenant Deleted");
        notification.setMessage(tenantName + " has been removed.");
        notification.setNotificationType("TENANT");
        notification.setReferenceType("TENANT");
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public Tenant getTenantById(Long id) {

        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));
    }

    @Override
    public List<Tenant> getAllTenants() {

        return tenantRepository.findAll();
    }

}
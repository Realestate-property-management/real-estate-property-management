package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Property;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.service.PropertyService;
import java.time.LocalDateTime;

import com.example.demo.models.AuditLog;
import com.example.demo.models.Notification;
import com.example.demo.models.User;

import com.example.demo.repository.AuditLogRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Property saveProperty(Property property) {

        property.setCreatedAt(LocalDateTime.now());
        property.setUpdatedAt(LocalDateTime.now());

        Property savedProperty = propertyRepository.save(property);

        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Audit Log
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("PROPERTY_CREATED");
        auditLog.setEntityType("PROPERTY");
        auditLog.setEntityId(savedProperty.getPropertyId());
        auditLog.setDescription("Property " + savedProperty.getPropertyName() + " created.");
        auditLog.setIpAddress("127.0.0.1");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // Notification
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Property Added");
        notification.setMessage(savedProperty.getPropertyName() + " has been added.");
        notification.setNotificationType("PROPERTY");
        notification.setReferenceType("PROPERTY");
        notification.setReferenceId(savedProperty.getPropertyId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedProperty;
    }
    @Override
    public Property updateProperty(Long id, Property property) {

        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

        existingProperty.setPropertyName(property.getPropertyName());
        existingProperty.setPropertyCode(property.getPropertyCode());
        existingProperty.setAddressLine1(property.getAddressLine1());
        existingProperty.setAddressLine2(property.getAddressLine2());
        existingProperty.setCity(property.getCity());
        existingProperty.setState(property.getState());
        existingProperty.setPostalCode(property.getPostalCode());
        existingProperty.setCountry(property.getCountry());
        existingProperty.setPropertyType(property.getPropertyType());
        existingProperty.setTotalUnits(property.getTotalUnits());
        existingProperty.setOccupiedUnits(property.getOccupiedUnits());
        existingProperty.setYearBuilt(property.getYearBuilt());
        existingProperty.setManager(property.getManager());
        existingProperty.setStatus(property.getStatus());
        existingProperty.setUpdatedAt(property.getUpdatedAt());

     // Save updated property
        Property updatedProperty = propertyRepository.save(existingProperty);

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("PROPERTY_UPDATED");
        auditLog.setEntityType("PROPERTY");
        auditLog.setEntityId(updatedProperty.getPropertyId());
        auditLog.setDescription("Property " + updatedProperty.getPropertyName() + " updated.");
        auditLog.setIpAddress("127.0.0.1");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Property Updated");
        notification.setMessage(updatedProperty.getPropertyName() + " updated successfully.");
        notification.setNotificationType("PROPERTY");
        notification.setReferenceType("PROPERTY");
        notification.setReferenceId(updatedProperty.getPropertyId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedProperty;    }

    @Override
    public void deleteProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String propertyName = property.getPropertyName();

        propertyRepository.delete(property);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("PROPERTY_DELETED");
        auditLog.setEntityType("PROPERTY");
        auditLog.setEntityId(id);
        auditLog.setDescription("Property " + propertyName + " deleted.");
        auditLog.setIpAddress("127.0.0.1");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Property Deleted");
        notification.setMessage(propertyName + " has been deleted.");
        notification.setNotificationType("PROPERTY");
        notification.setReferenceType("PROPERTY");
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
    @Override
    public Property getPropertyById(Long id) {

        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
    }

    @Override
    public List<Property> getAllProperties() {

        return propertyRepository.findAll();
    }

}
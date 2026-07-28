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

    private static final String USER_NOT_FOUND = "User not found";
    private static final String PROPERTY_TYPE = "PROPERTY";
    private static final String PROPERTY_PREFIX = "Property ";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String PROPERTY_NOT_FOUND_MSG = "Property not found with ID: ";

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
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // Audit Log
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("PROPERTY_CREATED");
        auditLog.setEntityType(PROPERTY_TYPE);
        auditLog.setEntityId(savedProperty.getPropertyId());
        auditLog.setDescription(PROPERTY_PREFIX + savedProperty.getPropertyName() + " created.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // Notification
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Property Added");
        notification.setMessage(savedProperty.getPropertyName() + " has been added.");
        notification.setNotificationType(PROPERTY_TYPE);
        notification.setReferenceType(PROPERTY_TYPE);
        notification.setReferenceId(savedProperty.getPropertyId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return savedProperty;
    }
    @Override
    public Property updateProperty(Long id, Property property) {

        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROPERTY_NOT_FOUND_MSG + id));

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
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("PROPERTY_UPDATED");
        auditLog.setEntityType(PROPERTY_TYPE);
        auditLog.setEntityId(updatedProperty.getPropertyId());
        auditLog.setDescription(PROPERTY_PREFIX + updatedProperty.getPropertyName() + " updated.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Property Updated");
        notification.setMessage(updatedProperty.getPropertyName() + " updated successfully.");
        notification.setNotificationType(PROPERTY_TYPE);
        notification.setReferenceType(PROPERTY_TYPE);
        notification.setReferenceId(updatedProperty.getPropertyId());
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        return updatedProperty;    }

    @Override
    public void deleteProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROPERTY_NOT_FOUND_MSG + id));

        // Temporary user (replace with logged-in user later)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        String propertyName = property.getPropertyName();

        propertyRepository.delete(property);

        // ===========================
        // Audit Log
        // ===========================
        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);
        auditLog.setAction("PROPERTY_DELETED");
        auditLog.setEntityType(PROPERTY_TYPE);
        auditLog.setEntityId(id);
        auditLog.setDescription(PROPERTY_PREFIX + propertyName + " deleted.");
        auditLog.setIpAddress(DEFAULT_IP);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        // ===========================
        // Notification
        // ===========================
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle("Property Deleted");
        notification.setMessage(propertyName + " has been deleted.");
        notification.setNotificationType(PROPERTY_TYPE);
        notification.setReferenceType(PROPERTY_TYPE);
        notification.setReferenceId(id);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
    @Override
    public Property getPropertyById(Long id) {

        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROPERTY_NOT_FOUND_MSG + id));
    }

    @Override
    public List<Property> getAllProperties() {

        return propertyRepository.findAll();
    }

}
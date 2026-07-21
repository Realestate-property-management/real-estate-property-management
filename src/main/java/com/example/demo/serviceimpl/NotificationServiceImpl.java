package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Notification;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Long id, Notification notification) {

        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));

        existingNotification.setUser(notification.getUser());
        existingNotification.setTitle(notification.getTitle());
        existingNotification.setMessage(notification.getMessage());
        existingNotification.setNotificationType(notification.getNotificationType());
        existingNotification.setReferenceType(notification.getReferenceType());
        existingNotification.setReferenceId(notification.getReferenceId());
        existingNotification.setIsRead(notification.getIsRead());
        existingNotification.setCreatedAt(notification.getCreatedAt());
        existingNotification.setReadAt(notification.getReadAt());

        return notificationRepository.save(existingNotification);
    }

    @Override
    public void deleteNotification(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));

        notificationRepository.delete(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {

        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
    }

    @Override
    public List<Notification> getAllNotifications() {

        return notificationRepository.findAll();
    }
}
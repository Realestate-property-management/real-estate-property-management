package com.example.demo.service;

import java.util.List;
import com.example.demo.models.Notification;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    Notification updateNotification(Long id, Notification notification);

    void deleteNotification(Long id);

    Notification getNotificationById(Long id);

    List<Notification> getAllNotifications();

}
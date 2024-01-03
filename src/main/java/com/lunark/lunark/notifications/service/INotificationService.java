package com.lunark.lunark.notifications.service;

import com.lunark.lunark.notifications.model.Notification;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface INotificationService {
    Collection<Notification> getAllNotifications(Long accountId);
    Notification create(Notification notification);
    long getUnreadNotificationCount(String email);
}

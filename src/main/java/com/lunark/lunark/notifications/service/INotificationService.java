package com.lunark.lunark.notifications.service;

import com.lunark.lunark.notifications.model.Notification;
import com.lunark.lunark.properties.model.Property;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface INotificationService {
    Collection<Notification> getAllNotifications(Long accountId);
    Notification create(Notification notification);

    Notification createPropertyReviewNotification(Property property);

    long getUnreadNotificationCount(String email);
    void subscribe(ISubscriber subscriber);
}

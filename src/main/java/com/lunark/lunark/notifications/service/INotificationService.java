package com.lunark.lunark.notifications.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.notifications.model.Notification;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reviews.model.Review;

import java.util.Collection;
import java.util.Optional;

public interface INotificationService {
    Collection<Notification> getAllNotifications(Long accountId);
    Notification create(Notification notification);

    Notification createNotification(Review review);

    long getUnreadNotificationCount(String email);
    void subscribe(ISubscriber subscriber);

    void markAsRead(Long id);

    Optional<Notification> findById(Long id);
}

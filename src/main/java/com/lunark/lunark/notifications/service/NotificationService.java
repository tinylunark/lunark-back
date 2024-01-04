package com.lunark.lunark.notifications.service;

import com.lunark.lunark.notifications.model.Notification;
import com.lunark.lunark.notifications.model.NotificationType;
import com.lunark.lunark.notifications.repository.INotificationRepository;
import com.lunark.lunark.properties.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class NotificationService implements INotificationService {
    private INotificationRepository notificationRepository;
    private Clock clock;
    private List<ISubscriber> subscribers;

    @Autowired
    public NotificationService(INotificationRepository notificationRepository, Clock clock) {
        this.notificationRepository = notificationRepository;
        this.clock = clock;
        this.subscribers = new ArrayList<>();
    }

    @Override
    public Collection<Notification> getAllNotifications(Long accountId) {
        return this.notificationRepository.findByAccount_IdOrderByDateDesc(accountId);
    }

    @Override
    public Notification create(Notification notification) {
        // TODO: Notify controller to send notification
        // TODO: Take notification settings into account
        Notification newNotification = this.notificationRepository.saveAndFlush(notification);
        this.subscribers.forEach(subscriber -> subscriber.notify(newNotification));
        return newNotification;
    }

    @Override
    public Notification createPropertyReviewNotification(Property property) {
        Notification notification = new Notification(
                "New property review for " + property.getName(),
                ZonedDateTime.now(clock),
                NotificationType.PROPERTY_REVIEW,
                property.getHost());
        return this.create(notification);
    }

    @Override
    public long getUnreadNotificationCount(String email) {
        return this.notificationRepository.countByAccount_EmailAndRead(email, false);
    }

    @Override
    public void subscribe(ISubscriber subscriber) {
        this.subscribers.add(subscriber);
    }
}

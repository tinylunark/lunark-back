package com.lunark.lunark.notifications.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.notifications.model.Notification;
import com.lunark.lunark.notifications.model.NotificationType;
import com.lunark.lunark.notifications.repository.INotificationRepository;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reviews.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Collection<Notification> getAllNotifications(Long accountId) {
        Collection<Notification> notifications = this.notificationRepository.findByAccount_IdOrderByDateDesc(accountId);
        this.notificationRepository.markAllNotificationsAsRead(accountId);
        return notifications;
    }

    @Override
    public Notification create(Notification notification) {
        if(!this.shouldSendNotification(notification)) {
            return null;
        }
        Notification newNotification = this.notificationRepository.saveAndFlush(notification);
        this.subscribers.forEach(subscriber -> subscriber.notify(newNotification));
        return newNotification;
    }

    private boolean shouldSendNotification(Notification notification) {
        // TODO: Check if notification should be sent based on the notification settings of the recipient and type of notification
        return true;
    }
    @Override
    public Notification createNotification(Review review)  {
        String message = createReviewNotificationMessage(review);
        Notification notification = new Notification(
                message,
                ZonedDateTime.now(clock),
                review.getType().equals(Review.ReviewType.PROPERTY) ? NotificationType.PROPERTY_REVIEW : NotificationType.HOST_REVIEW,
                review.getType().equals(Review.ReviewType.PROPERTY)? review.getProperty().getHost() : review.getHost());
        return this.create(notification);
    }

    private String createReviewNotificationMessage(Review review) {
        if (review.getType().equals(Review.ReviewType.HOST)) {
            return "New host review";
        }
        return "New property review for " + review.getProperty().getName();
    }

    @Override
    public long getUnreadNotificationCount(String email) {
        return this.notificationRepository.countByAccount_EmailAndRead(email, false);
    }

    @Override
    public void subscribe(ISubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        this.notificationRepository.markAllNotificationAsRead(id);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return this.notificationRepository.findById(id);
    }

}

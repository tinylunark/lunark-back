package com.lunark.lunark.notifications.service;

import com.lunark.lunark.notifications.model.Notification;
import com.lunark.lunark.notifications.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class NotificationService implements INotificationService {
    private INotificationRepository notificationRepository;

    @Autowired
    public NotificationService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Collection<Notification> getAllNotifications(Long accountId) {
        return this.notificationRepository.findByAccount_Id(accountId);
    }

    @Override
    public Notification create(Notification notification) {
        // TODO: Notify controller to send notification
        // TODO: Take notification settings into account
        return this.notificationRepository.saveAndFlush(notification);
    }

    @Override
    public long getUnreadNotificationCount(String email) {
        return this.notificationRepository.countByAccount_EmailAndRead(email, false);
    }
}

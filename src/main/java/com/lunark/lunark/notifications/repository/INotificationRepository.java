package com.lunark.lunark.notifications.repository;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
    long countByAccount_EmailAndRead(String email, boolean read);
}

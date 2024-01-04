package com.lunark.lunark.notifications.repository;

import com.lunark.lunark.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    long countByAccount_EmailAndRead(String email, boolean read);
    Collection<Notification> findByAccount_IdOrderByDateDesc(Long id);
}

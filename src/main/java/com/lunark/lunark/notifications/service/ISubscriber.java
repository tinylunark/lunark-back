package com.lunark.lunark.notifications.service;

import com.lunark.lunark.notifications.model.Notification;

public interface ISubscriber {
    void notify(Notification notification);
}

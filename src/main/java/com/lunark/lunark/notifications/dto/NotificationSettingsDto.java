package com.lunark.lunark.notifications.dto;

import com.lunark.lunark.notifications.model.NotificationType;
import lombok.Data;

@Data
public class NotificationSettingsDto {
    NotificationType type;
}

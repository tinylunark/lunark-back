package com.lunark.lunark.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestNotificationSettingsDto {
    private boolean notifyOnReservationRequestResponse;
}

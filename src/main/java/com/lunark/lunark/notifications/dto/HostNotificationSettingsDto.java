package com.lunark.lunark.notifications.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostNotificationSettingsDto {
    private boolean notifyOnReservationCreation;
    private boolean notifyOnReservationCancellation;
    private boolean notifyOnHostReview;
    private boolean notifyOnPropertyReview;
}

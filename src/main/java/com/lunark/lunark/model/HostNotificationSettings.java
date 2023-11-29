package com.lunark.lunark.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HostNotificationSettings {
    private boolean notifyOnReservationCreation;
    private boolean notifyOnReservationCancellation;
    private boolean notifyOnHostReview;
    private boolean notifyOnPropertyReview;
    private Account host;
}

package com.lunark.lunark.notifications.model;

import com.lunark.lunark.auth.model.Account;
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

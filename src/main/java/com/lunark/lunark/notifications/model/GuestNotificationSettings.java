package com.lunark.lunark.notifications.model;

import com.lunark.lunark.auth.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GuestNotificationSettings {
    private boolean notifyOnReservationRequestResponse;
    private Account guest;
}

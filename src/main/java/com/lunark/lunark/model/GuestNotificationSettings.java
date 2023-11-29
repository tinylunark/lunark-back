package com.lunark.lunark.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GuestNotificationSettings {
    private boolean notifyOnReservationRequestResponse;
    private Account guest;
}

package com.lunark.lunark.notifications.model;

import com.lunark.lunark.auth.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class GuestNotificationSettings {
    @Column(columnDefinition = "boolean default true")
    private boolean notifyOnReservationRequestResponse;
}

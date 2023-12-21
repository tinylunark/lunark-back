package com.lunark.lunark.notifications.controller;

import com.lunark.lunark.notifications.dto.GuestNotificationSettingsDto;
import com.lunark.lunark.notifications.dto.HostNotificationSettingsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class NotificationSettingsController {
    @PutMapping(value = "/{host_id}/notification-settings", params = {"reservation-creation", "reservation-cancellation", "host-review", "property-review"})
    public ResponseEntity<HostNotificationSettingsDto> changeHostNotificationSettings(@RequestParam("reservation-creation") boolean notifyOnReservationCreation, @RequestParam("reservation-cancellation") boolean notifyOnReservationCancellation, @RequestParam("host-review") boolean notifyOnHostReview, @RequestParam("property-review") boolean notifyOnPropertyReview) {
        //TODO: Check if id belongs to a host
        HostNotificationSettingsDto hostNotificationSettingsDto = new HostNotificationSettingsDto(notifyOnReservationCreation, notifyOnReservationCancellation,notifyOnHostReview, notifyOnPropertyReview);
        return new ResponseEntity<>(hostNotificationSettingsDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{guest_id}/notification-settings", params = {"reservation-response"})
    public ResponseEntity<GuestNotificationSettingsDto> changeHostNotificationSettings(@RequestParam("reservation-response") boolean notifyOnReservationRequestResponse) {
        //TODO: Check if id belongs to guest
        GuestNotificationSettingsDto guestNotificationSettingsDto = new GuestNotificationSettingsDto(notifyOnReservationRequestResponse);
        return new ResponseEntity<>(guestNotificationSettingsDto, HttpStatus.OK);
    }
}

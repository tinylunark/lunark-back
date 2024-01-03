package com.lunark.lunark.notifications.controller;

import com.lunark.lunark.notifications.dto.NotificationResponseDto;
import com.lunark.lunark.notifications.dto.UnreadNotificationCountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    private void handleSessionSubscribed(SessionSubscribeEvent event) {
        Map<String, String> message = new HashMap<>();
        message.put("unreadNotificationCount", "5");
        System.out.println("Sending message");
        simpMessagingTemplate.convertAndSendToUser(event.getUser().getName(), "/socket-publisher",  message);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationResponseDto>> findAllForUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(List.of(new NotificationResponseDto()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationResponseDto> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

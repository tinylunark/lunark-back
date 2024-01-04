package com.lunark.lunark.notifications.controller;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.notifications.dto.NotificationResponseDto;
import com.lunark.lunark.notifications.dto.UnreadNotificationCountDto;
import com.lunark.lunark.notifications.model.Notification;
import com.lunark.lunark.notifications.repository.INotificationRepository;
import com.lunark.lunark.notifications.service.INotificationService;
import com.lunark.lunark.notifications.service.ISubscriber;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/notifications")
public class NotificationController implements ISubscriber {
    private SimpMessagingTemplate simpMessagingTemplate;

    private INotificationService notificationService;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public NotificationController(SimpMessagingTemplate simpMessagingTemplate, INotificationService notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
        this.notificationService.subscribe(this);
    }

    @EventListener
    void handleSessionSubscribed(SessionSubscribeEvent event) {
        Map<String, String> message = new HashMap<>();
        String email = event.getUser().getName();
        long unreadNotificationCount = notificationService.getUnreadNotificationCount(email);
        message.put("unreadNotificationCount", Long.toString(unreadNotificationCount));
        System.out.println("Sending message");
        simpMessagingTemplate.convertAndSendToUser(email, "/socket-publisher",  message);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST') or hasAuthority('HOST')")
    public ResponseEntity<List<NotificationResponseDto>> findAllForUser(@PathVariable("id") Long userId) {
        Collection<Notification> notifications = notificationService.getAllNotifications(userId);
        List<NotificationResponseDto> notificationDtos = notifications.stream().map(notification -> modelMapper.map(notification, NotificationResponseDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST') or hasAuthority('HOST')")
    public ResponseEntity<List<NotificationResponseDto>> findAllForCurrentUser() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<Notification> notifications = notificationService.getAllNotifications(account.getId());
        List<NotificationResponseDto> notificationDtos = notifications.stream().map(notification -> modelMapper.map(notification, NotificationResponseDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationResponseDto> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public void notify(Notification notification) {
        String recipient = notification.getAccount().getEmail();
        Map<String, String> message = this.createMessage(notification);
        this.simpMessagingTemplate.convertAndSendToUser(recipient, "/socket-publisher", message);
    }

    public Map<String, String> createMessage(Notification notification) {
        Map<String, String> message = new HashMap<>();
        message.put("type", notification.getType().toString());
        message.put("text", notification.getText());
        message.put("date", notification.getDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return message;
    }

}

package com.lunark.lunark.notifications.controller;

import com.lunark.lunark.notifications.dto.NotificationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NotificationResponseDto>> findAllForUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(List.of(new NotificationResponseDto()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationResponseDto> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

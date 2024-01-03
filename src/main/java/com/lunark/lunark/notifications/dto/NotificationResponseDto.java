package com.lunark.lunark.notifications.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class NotificationResponseDto {
    Long id;
    String text;
    Date date;
    boolean read;
    Long accountId;
}

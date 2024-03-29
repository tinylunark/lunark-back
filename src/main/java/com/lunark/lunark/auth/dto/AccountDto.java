package com.lunark.lunark.auth.dto;

import com.lunark.lunark.auth.model.ProfileImage;
import com.lunark.lunark.notifications.model.GuestNotificationSettings;
import com.lunark.lunark.notifications.model.HostNotificationSettings;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String address;
    private String country;
    private String phoneNumber;
    private Date birthday;
    private String role;
    private boolean verified;
    private boolean blocked;
    private ProfileImage profileImage;
    private Double averageRating;
    private int cancelCount;
    private GuestNotificationSettings guestNotificationSettings;
    private HostNotificationSettings hostNotificationSettings;
}

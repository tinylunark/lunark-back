package com.lunark.lunark.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String role;
    private boolean verified;
    private boolean blocked;
}

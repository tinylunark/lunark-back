package com.lunark.lunark.dto;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.AccountRole;

public class AccountDto {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String role;

    public Account toAccount() {
        AccountRole role = AccountRole.fromString(this.role);
        return new Account(null, email, password, name, surname, address, phoneNumber, false, role, false, false);
    }
}

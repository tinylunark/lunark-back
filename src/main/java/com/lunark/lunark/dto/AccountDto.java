package com.lunark.lunark.dto;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.AccountRole;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

public class AccountDto {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String role;

    public AccountDto() {

    }

    public AccountDto(Account account) {
        this.email = account.getEmail();
        this.role = account.getRole().toString();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Account toAccount() {
        AccountRole role = AccountRole.fromString(this.role);
        return new Account(null, email, password, name, surname, address, phoneNumber, false, role, false, false, new ArrayList<>());
    }
}

package com.lunark.lunark.model;

import jdk.jshell.spi.ExecutionControl;

public class Account {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private boolean verified;
    private AccountRole role;
    private boolean notificationsEnabled;
    private boolean blocked;

    public Account() {

    }

    public Account(Long id, String email, String password, String name, String surname, String address, String phoneNumber, boolean verified, AccountRole role, boolean notificationsEnabled, boolean blocked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.verified = verified;
        this.role = role;
        this.notificationsEnabled = notificationsEnabled;
    }

    public void verify() {
        verified = true;
    }
    public boolean canLogIn() {
        return verified && !blocked;
    }

    public boolean credentialsMatch(String email, String password) {
        return this.email.equals(email) && this.email.equals(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

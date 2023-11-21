package com.lunark.lunark.model;

import jdk.jshell.spi.ExecutionControl;

public class Account {
    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private boolean verified;
    private AccountRole role;
    private boolean notificationsEnabled;

    public Account() {

    }

    public Account(int id, String email, String password, String name, String surname, String address, String phoneNumber, boolean verified, AccountRole role, boolean notificationsEnabled) {
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
    public boolean canLogIn() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented yet.");
    }

}

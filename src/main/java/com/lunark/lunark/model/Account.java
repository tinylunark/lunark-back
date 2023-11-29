package com.lunark.lunark.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;
import java.util.Set;

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
    private Collection<Review> reviews;
    private Set<Property> favoriteProperties;
    public Account() {

    }



    public Account(Long id, String email, String password, String name, String surname, String address, String phoneNumber, boolean verified, AccountRole role, boolean notificationsEnabled, boolean blocked, Collection<Review> reviews, Set<Property> favoriteProperties) {
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
        this.blocked = blocked;
        this.reviews = reviews;
        this.favoriteProperties = favoriteProperties;
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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Property> getFavoriteProperties() {
        return favoriteProperties;
    }

    public void setFavoriteProperties(Set<Property> favoriteProperties) {
        this.favoriteProperties = favoriteProperties;
    }


}

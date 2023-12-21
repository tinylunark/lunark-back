package com.lunark.lunark.notifications.model;

import com.lunark.lunark.auth.model.Account;

import java.util.Date;

public class Notification {
    private Long id;
    private String text;
    private Date date;
    private boolean received;
    private Account account;

    public Notification() {
    }

    public Notification(Long id, String text, Date date, boolean received, Account account) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.received = received;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}

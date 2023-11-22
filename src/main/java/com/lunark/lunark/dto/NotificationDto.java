package com.lunark.lunark.dto;

import java.util.Date;

public class NotificationDto {
    private Long id;
    private String text;
    private Date date;
    private boolean received;
    private Long accountId;

    public NotificationDto() {
    }

    public NotificationDto(Long id, String text, Date date, boolean received, Long accountId) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.received = received;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}

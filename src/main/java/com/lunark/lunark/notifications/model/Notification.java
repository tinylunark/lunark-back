package com.lunark.lunark.notifications.model;

import com.lunark.lunark.auth.model.Account;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String text;
    @Column
    private Date date;
    @Column
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    public Notification() {
    }

    public Notification(Long id, String text, Date date, boolean read, Account account) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.read = read;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}

package com.lunark.lunark.auth.service;

public interface IEmailService {
    void send(String to, String subject, String content);
}

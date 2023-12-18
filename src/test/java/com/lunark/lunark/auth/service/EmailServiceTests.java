package com.lunark.lunark.auth.service;

import com.lunark.lunark.configuration.AppConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EmailServiceTests {
    @Test
    @Disabled
    public void sendMail() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        EmailService emailService = context.getBean(EmailService.class);
        emailService.send("161ibbpgf@mozmail.com", "My first message from java");
    }
}

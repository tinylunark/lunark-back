package com.lunark.lunark.auth.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService implements IEmailService {
    private Properties prop;
    private Session session;

    @Value("${email.username}")
    @Setter
    public String username;

    @Value("${email.password}")
    @Setter
    public String password;

    @Value("${email.smtp-server}")
    @Setter
    public String smtpServer;

    @Value("${email.domain}")
    @Setter
    public String domain;

    public EmailService() {
    }

    public void setUpProperties() {
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", smtpServer);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", smtpServer);
    }

    public void setUpSession() {
        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    @Override
    public void send(String to, String content) {
        this.setUpProperties();
        this.setUpSession();
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username+"@"+domain));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Mail Subject");
            String msg = content;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("ERROR: Could not send mail");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}

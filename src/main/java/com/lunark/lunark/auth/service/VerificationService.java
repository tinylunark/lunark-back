package com.lunark.lunark.auth.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.model.VerificationLink;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.auth.repository.IVerificationLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.Clock;
import java.util.Optional;

@Service
public class VerificationService implements IVerificationService {
    private IVerificationLinkRepository verificationLinkRepository;
    private IEmailService emailService;
    private Clock clock;

    @Autowired
    public VerificationService(IVerificationLinkRepository verificationLinkRepository, IEmailService emailService, Clock clock) {
        this.verificationLinkRepository = verificationLinkRepository;
        this.emailService = emailService;
        this.clock = clock;
    }

    @Override
    public void createVerificationLink(Account account) {
        VerificationLink verificationLink = verificationLinkRepository.saveAndFlush(new VerificationLink(account, Date.from(clock.instant())));
        emailService.send(account.getEmail(), getVerificationEmail(account.getEmail(), verificationLink.getId()));
    }

    @Override
    public boolean verify(Long linkId) {

        Optional<VerificationLink> verificationLinkOptional = this.verificationLinkRepository.findById(linkId);
        if (verificationLinkOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid verification link");
        }
        VerificationLink verificationLink = verificationLinkOptional.get();
        if(verificationLink.tryUse()) {
            verificationLinkRepository.saveAndFlush(verificationLink);
            return true;
        }
        return false;
    }

    private String getVerificationEmail(String email, Long linkId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n").append("<html>\n");
        stringBuilder.append("<head>\n<title>Lunark account confirmation instruction</title>\n</head>");
        stringBuilder.append("<body>\n");
        String welcomeLine = "Welcome " + email+ "!";
        stringBuilder.append("<p>").append(welcomeLine).append("</p>\n");

        stringBuilder.append("<p>Thank you for signing up for Lunark.</p>\n" +
                "<p>Verify your email address by clicking the link below.").append("</p>\n");

        String frontendLink = "http://localhost:4200/verify/"+linkId;
        stringBuilder.append(String.format("<a href=\"%s\">%s</a>", frontendLink, frontendLink)).append("<br/>\n");

        stringBuilder.append("<p>Note that unverified accounts are automatically deleted 24 " +
                "hours after signup.</p>\n" +
                "<p>If you didn't request this, please ignore this email.</p>\n" +
                "<p>Sincerely,<br/>\n" +
                "Lunark Team</p>\n");
        stringBuilder.append("</body>\n</html>\n");
        return stringBuilder.toString();
    }
}

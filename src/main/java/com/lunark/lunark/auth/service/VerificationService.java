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
    private IAccountRepository accountRepository;
    private Clock clock;

    @Autowired
    public VerificationService(IVerificationLinkRepository verificationLinkRepository, IAccountRepository accountRepository, Clock clock) {
        this.verificationLinkRepository = verificationLinkRepository;
        this.accountRepository = accountRepository;
        this.clock = clock;
    }

    @Override
    public void createVerificationLink(Account account) {
        verificationLinkRepository.saveAndFlush(new VerificationLink(account, Date.from(clock.instant())));
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
}

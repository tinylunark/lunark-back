package com.lunark.lunark.auth.service;

import com.lunark.lunark.auth.model.Account;

public interface IVerificationService {
    void createVerificationLink(Account account);
    boolean verify(Long linkId);
}

package com.lunark.lunark.service;

import com.lunark.lunark.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Collection<Account> findAll();
    Account create(Account account);
    Optional<Account> find(Long id);
    Optional<Account> find(String username, String password);
    Account update(Account account);
    void delete(Long id);
    Double getAverageGrade(Long id);
}

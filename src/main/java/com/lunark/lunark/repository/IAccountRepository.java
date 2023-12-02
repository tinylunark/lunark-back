package com.lunark.lunark.repository;

import com.lunark.lunark.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface IAccountRepository {
    Collection<Account> findAll();
    Account create(Account account);
    Optional<Account> find(Long id);
    Optional<Account> find(String email, String password);
    Account update(Account account);
    void delete(Long id);

}

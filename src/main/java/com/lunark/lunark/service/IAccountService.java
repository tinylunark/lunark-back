package com.lunark.lunark.service;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.Property;

import java.util.Collection;
import java.util.Optional;

public interface IAccountService {
    Collection<Account> findAll();
    Account create(Account account);
    Optional<Account> find(Long id);
    Optional<Account> find(String username, String password);
    Account update(Account account);
    void delete(Long id);
    void addToFavorites(Long id, Property property);
    Double getAverageGrade(Long id);
}

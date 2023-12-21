package com.lunark.lunark.auth.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;

import java.util.Collection;
import java.util.Optional;

public interface IAccountService {
    Collection<Account> findAll();
    Account create(Account account);
    Optional<Account> find(Long id);
    Optional<Account> find(String username, String password);
    Optional<Account> find(String email);
    Account update(Account account);
    boolean delete(Long id);
    boolean updatePassword(Long id, String oldPassword, String newPassword);
    void addToFavorites(Long id, Property property);
    Double getAverageGrade(Long id);
}

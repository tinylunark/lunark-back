package com.lunark.lunark.service;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;


    @Override
    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account create(Account account) {
        return accountRepository.create(account);
    }

    @Override
    public Optional<Account> find(Long id) {
        return accountRepository.find(id);
    }

    @Override
    public Optional<Account> find(String email, String password) {
        return accountRepository.find(email, password);
    }

    @Override
    public Account update(Account account) {
        return accountRepository.update(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.delete(id);
    }
}

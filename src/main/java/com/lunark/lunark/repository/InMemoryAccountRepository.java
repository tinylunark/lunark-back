package com.lunark.lunark.repository;

import com.lunark.lunark.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAccountRepository implements IAccountRepository {
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
    @Override
    public Collection<Account> findAll() {
        return this.accounts.values();
    }

    @Override
    public Account create(Account account) {
        Long id = account.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            account.setId(id);
        }

        this.accounts.put(id, account);
        return account;
    }

    @Override
    public Optional<Account> find(Long id) {
        return Optional.ofNullable(this.accounts.get(id));
    }

    @Override
    public Optional<Account> find(String email, String password) {
        for (Account account: this.accounts.values()) {
            if (account.canLogIn() && account.credentialsMatch(email, password)) {
                return Optional.of(account);
            }
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Account update(Account account) {
        Long id = account.getId();

        if (id != null) {
            this.accounts.put(id, account);
        }

        return account;
    }

    @Override
    public void delete(Long id) {
        this.accounts.remove(id);
    }
}

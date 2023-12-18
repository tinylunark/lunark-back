package com.lunark.lunark.auth.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.auth.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }


    @Override
    public Account create(Account account) {
        account.setVerified(false);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public Optional<Account> find(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> find(String email, String password) {
        return accountRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Optional<Account> find(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account update(Account account) {
        Optional<Account> oldAccountOptional = accountRepository.findById(account.getId());
        if(oldAccountOptional.isEmpty()) {
            return null;
        }
        Account oldAccount = oldAccountOptional.get();
        if(account.getPassword() == null) {
            account.setPassword(oldAccount.getPassword());
        } else {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        return accountRepository.saveAndFlush(account);
    }

    @Override public void delete(Long id) {
        accountRepository.deleteById(id);
    }


    @Override
    public Double getAverageGrade(Long id) {
        Optional<Account> account = this.find(id);
        if (account.isEmpty()) {
            return null;
        }
        Account foundAccount = account.get();
        return calculateAverageGrade(foundAccount);
    }

    private Double calculateAverageGrade(Account account) {
        ArrayList<Review> reviewList = (ArrayList<Review>) account.getReviews();
        if (reviewList.isEmpty()) {
            return 0.0;
        }
        double sum = reviewList.stream().mapToDouble(Review::getRating).sum();
        return sum / reviewList.size();
    }

    @Override
    public void addToFavorites(Long id, Property property) {
        this.find(id).ifPresent(account -> {
            account.getFavoriteProperties().add(property);
            update(account);
        });
    }
}

package com.lunark.lunark.service;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.Property;
import com.lunark.lunark.model.Review;
import com.lunark.lunark.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    IAccountRepository accountRepository;


    @Override
    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account create(Account account) {
        account.setVerified(false);
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
    public Account update(Account account) {
        if(accountRepository.findById(account.getId()).isEmpty()) {
            return null;
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

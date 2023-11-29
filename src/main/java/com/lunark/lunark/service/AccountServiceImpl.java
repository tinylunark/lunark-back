package com.lunark.lunark.service;

import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.Review;
import com.lunark.lunark.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override public void delete(Long id) { accountRepository.delete(id); }


    @Override
    public Double getAverageGrade(Long id) {
        Optional<Account> account = this.find(id);
        if (account.isEmpty()) {
            return -1.0;
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
}

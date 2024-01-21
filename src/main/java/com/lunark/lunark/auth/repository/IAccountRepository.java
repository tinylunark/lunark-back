package com.lunark.lunark.auth.repository;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    Optional<Account> findByEmailAndPassword(String email, String password);
    Optional<Account> findByEmail(String email);
}
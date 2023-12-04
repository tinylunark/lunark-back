package com.lunark.lunark.repository;

import com.lunark.lunark.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    Optional<Account> findByEmailAndPassword(String email, String password);
}

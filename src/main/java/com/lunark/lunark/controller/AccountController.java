package com.lunark.lunark.controller;

import com.lunark.lunark.dto.AccountDto;
import com.lunark.lunark.model.Account;
import com.lunark.lunark.repository.AccountRepository;
import com.lunark.lunark.service.AccountService;
import com.lunark.lunark.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    VerificationService verificationService;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
        Optional<Account> account = accountService.find(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.create(accountDto.toAccount());
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
}

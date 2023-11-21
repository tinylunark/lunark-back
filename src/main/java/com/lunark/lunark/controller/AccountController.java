package com.lunark.lunark.controller;

import com.lunark.lunark.dto.AccountDto;
import com.lunark.lunark.mapper.AccountDtoMapper;
import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.AccountRole;
import com.lunark.lunark.repository.AccountRepository;
import com.lunark.lunark.service.AccountService;
import com.lunark.lunark.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/nonadmins", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> getNonAdmins(SpringDataWebProperties.Pageable pageable) {
        List<Account> nonAdmins = accountService.findAll().stream().filter(account -> account.getRole() != AccountRole.ADMIN).collect(Collectors.toList());

        List<AccountDto> accountDtos = nonAdmins.stream().map(AccountDtoMapper::fromAccountToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

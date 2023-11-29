package com.lunark.lunark.controller;

import com.lunark.lunark.dto.AccountDto;
import com.lunark.lunark.dto.AccountVerifiedDto;
import com.lunark.lunark.mapper.AccountDtoMapper;
import com.lunark.lunark.model.Account;
import com.lunark.lunark.model.AccountRole;
import com.lunark.lunark.service.AccountService;
import com.lunark.lunark.service.PropertyService;
import com.lunark.lunark.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    PropertyService propertyService;

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id) {
        Optional<Account> account = accountService.find(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccountDto(account.get()), HttpStatus.OK);
    }

    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        Account newAccount = accountDto.toAccount();
        newAccount.verify();
        Account account = accountService.create(newAccount);
        return new ResponseEntity<>(new AccountDto(account), HttpStatus.CREATED);
    }

    @GetMapping(path="/nonadmins", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> getNonAdmins(SpringDataWebProperties.Pageable pageable) {
        List<Account> nonAdmins = accountService.findAll().stream().filter(account -> account.getRole() != AccountRole.ADMIN).collect(Collectors.toList());

        List<AccountDto> accountDtos = nonAdmins.stream().map(AccountDtoMapper::fromAccountToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto, @PathVariable("id") Long id) {
        Optional<Account> accountOptional = accountService.find(id);
        if(accountOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account account = accountDto.toAccount();
        account.setId(id);
        accountService.update(account);

        return new ResponseEntity<>(new AccountDto(account), HttpStatus.OK);
    }

    @PostMapping(path="/verify/{verification_link_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountVerifiedDto> verifyAccount(@PathVariable("verification_link_id") Long verifcationLinkId) {
        Optional<Account> account = accountService.find(verifcationLinkId);
        if(account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account verifiedAccount = account.get();
        verifiedAccount.verify();
        return new ResponseEntity<>(new AccountVerifiedDto(accountService.update(verifiedAccount)), HttpStatus.OK);
    }

    @PatchMapping(path="/{id}/add-favorite/{propertyId}")
    public ResponseEntity<AccountDto> addPropertyToFavorites(@PathVariable("id") Long accountId, @PathVariable("propertyId") Long propertyId) {
        Optional<Account> accountOptional = accountService.find(accountId);
        if(accountOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Account account = accountOptional.get();
        accountService.addToFavorites(accountId, propertyService.find(propertyId).orElse(null));
        return new ResponseEntity<>(new AccountDto(account), HttpStatus.OK);
    }
}

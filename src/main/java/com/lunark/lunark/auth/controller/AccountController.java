package com.lunark.lunark.auth.controller;

import com.lunark.lunark.auth.dto.AccountDto;
import com.lunark.lunark.auth.dto.AccountSignUpDto;
import com.lunark.lunark.auth.dto.AccountVerifiedDto;
import com.lunark.lunark.mapper.AccountDtoMapper;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.model.AccountRole;
import com.lunark.lunark.auth.service.IAccountService;
import com.lunark.lunark.properties.service.IPropertyService;
import com.lunark.lunark.auth.service.VerificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    IAccountService accountService;

    @Autowired
    VerificationService verificationService;

    @Autowired
    IPropertyService propertyService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id) {
        Optional<Account> account = accountService.find(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(modelMapper.map(account.get(), AccountDto.class), HttpStatus.OK);
    }
  
    @GetMapping(value ="/average/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAverageGrade(@PathVariable("id") Long id) {
        Double averageGrade = accountService.getAverageGrade(id);
        if (averageGrade == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }

    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountSignUpDto accountDto) {
        Account newAccount = accountDto.toAccount();
        newAccount.verify();
        Account account = accountService.create(newAccount);
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.class), HttpStatus.CREATED);
    }

    @GetMapping(path="/nonadmins", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> getNonAdmins(SpringDataWebProperties.Pageable pageable) {
        List<Account> nonAdmins = accountService.findAll().stream().filter(account -> account.getRole() != AccountRole.ADMIN).collect(Collectors.toList());
        List<AccountDto> accountDtos = nonAdmins.stream().map(AccountDtoMapper::fromAccountToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<AccountDto> deleteAccount(@PathVariable("id") Long id) {
        if(accountService.find(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PutMapping(path="/{id}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountSignUpDto accountDto, @PathVariable("id") Long id) {
        Optional<Account> accountOptional = accountService.find(id);
        if(accountOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Account account = accountDto.toAccount();
        account.setId(id);
        accountService.update(account);
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.class), HttpStatus.OK);
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

    @PostMapping(value = "/block/{id}")
    public ResponseEntity<?> blockAccount(@PathVariable("id") Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path="/{id}/add-favorite/{propertyId}")
    public ResponseEntity<AccountSignUpDto> addPropertyToFavorites(@PathVariable("id") Long accountId, @PathVariable("propertyId") Long propertyId) {
        Optional<Account> accountOptional = accountService.find(accountId);
        if(accountOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Account account = accountOptional.get();
        accountService.addToFavorites(accountId, propertyService.find(propertyId).orElse(null));
        return new ResponseEntity<>(new AccountSignUpDto(account), HttpStatus.OK);
    }
}

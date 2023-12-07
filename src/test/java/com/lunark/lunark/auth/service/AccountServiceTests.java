package com.lunark.lunark.auth.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.model.AccountRole;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.auth.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account = new Account(6L, "host@example.com", "pass", "Mirna", "Studsluzvic", "Trg Dositeja Obradovica 6, Novi Sad", "021555555", true, AccountRole.HOST, false, false, null, null);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        account = new Account(6L, "host@example.com", "pass", "Mirna", "Studsluzbic", "Trg Dositeja Obradovica 6, Novi Sad", "021555555", true, AccountRole.HOST, false, false, null, null);
    }

    @Test
    public void testAdd() {
        Mockito.when(accountRepository.saveAndFlush(account)).thenReturn(account);
        Assertions.assertSame(account, accountService.create(account));
        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        Assertions.assertSame(account, accountService.find(account.getId()).get());
    }

    @Test
    public void testUpdate() {
        account.setEmail("email");
        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.saveAndFlush(account)).thenReturn(account);
        Assertions.assertSame(account, accountService.update(account));
    }

    @Test
    public void testDelete() {
        accountService.delete(account.getId());
        Mockito.doReturn(Optional.ofNullable(null)).when(accountRepository).findById(account.getId());
        Assertions.assertTrue(accountService.find(account.getId()).isEmpty());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6, true",
            "5, false"
    })
    public void testFindById(int id, boolean found) {
        Mockito.lenient().when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        Long accountId = (long) id;
        Assertions.assertEquals(found, accountService.find(accountId).isPresent());
    }
    @ParameterizedTest
    @CsvSource(value = {
            "host@example.com, pass, true",
            "host@example.com, wrongpass, false",
            "wrongemail, pass, false",
            "wrongemail, wrongpass, false",
    })
    public void testFindByEmailAndPassword(String email, String password, boolean found) {
        Mockito.lenient().when(accountRepository.findByEmailAndPassword(account.getEmail(), account.getPassword())).thenReturn(Optional.of(account));
        Assertions.assertEquals(found, accountService.find(email, password).isPresent());
    }
}

package com.lunark.lunark.security;

import com.lunark.lunark.auth.dto.AccountSignUpDto;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.service.AccountService;
import com.lunark.lunark.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="api/auth", produces= MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @PostMapping(value="/login", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenState> createAuthenticationToken( @RequestBody AuthRequest authenticationRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account account  = (Account) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(account.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping(value="/signup", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> addUser(@RequestBody AccountSignUpDto userRequest, UriComponentsBuilder ucBuilder) {
        Optional<Account> existUser = this.accountService.find(userRequest.getEmail());
        Account account = this.accountService.create(userRequest.toAccount());
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }
}

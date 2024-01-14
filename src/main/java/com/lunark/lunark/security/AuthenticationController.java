package com.lunark.lunark.security;

import com.lunark.lunark.auth.dto.AccountSignUpDto;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.service.AccountService;
import com.lunark.lunark.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

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
    public ResponseEntity<UserTokenState> createAuthenticationToken(@Valid @RequestBody AuthRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account account  = (Account) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(account);
        int expiresIn = tokenUtils.getExpiredIn();
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping(value="/signup", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> addUser(@RequestBody AccountSignUpDto userRequest, UriComponentsBuilder ucBuilder) {
        Optional<Account> existUser = this.accountService.find(userRequest.getEmail());
        Account account = this.accountService.create(userRequest.toAccount());
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/logout",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity logoutUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Lunark user is not authenticated!", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.lunark.lunark.auth.controller;

import com.lunark.lunark.auth.dto.AccountLoginDto;
import com.lunark.lunark.auth.dto.AuthenticationToken;
import com.lunark.lunark.auth.service.IAccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    IAccountService accountService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationToken> login(@RequestBody AccountLoginDto accountLoginDto, HttpServletResponse response) {
        return new ResponseEntity<>(new AuthenticationToken("5VD74ska6lP5fxwX6lRy", "5VD74ska6lP5fxwX6lRy"), HttpStatus.OK);
    }
}

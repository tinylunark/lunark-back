package com.lunark.lunark.controller;

import com.lunark.lunark.dto.AccountLoginDto;
import com.lunark.lunark.dto.AuthenticationToken;
import com.lunark.lunark.service.AccountService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    AccountService accountService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationToken> login(@RequestBody AccountLoginDto accountLoginDto, HttpServletResponse response) {
        return new ResponseEntity<>(new AuthenticationToken("5VD74ska6lP5fxwX6lRy", "5VD74ska6lP5fxwX6lRy"), HttpStatus.OK);
    }
}

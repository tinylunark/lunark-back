package com.lunark.lunark.controller;

import com.lunark.lunark.dto.AccountReportRequestDto;
import com.lunark.lunark.dto.AccountReportResponseDto;
import com.lunark.lunark.dto.ReviewReportRequestDto;
import com.lunark.lunark.dto.ReviewReportResponseDto;
import com.lunark.lunark.service.AccountReportService;
import com.lunark.lunark.service.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reports/accounts")
public class AccountReportController {
    @Autowired
    private AccountReportService accountReportService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountReportResponseDto> getAccountReport(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new AccountReportResponseDto(null, null, null, null), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountReportResponseDto>> getAll() {
        return new ResponseEntity<>(List.of(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountReportResponseDto> createAccountReport(@RequestBody AccountReportRequestDto dto) {
        return new ResponseEntity<>(new AccountReportResponseDto(null, null, null, null), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountReportResponseDto> updateAccountReport(@PathVariable Long id, @RequestBody AccountReportRequestDto dto) {
        return new ResponseEntity<>(new AccountReportResponseDto(null, null, null, null), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountReportResponseDto> deleteAccountReport(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

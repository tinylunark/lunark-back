package com.lunark.lunark.moderation.controller;

import com.lunark.lunark.moderation.dto.AccountReportRequestDto;
import com.lunark.lunark.moderation.dto.AccountReportResponseDto;
import com.lunark.lunark.moderation.model.AccountReport;
import com.lunark.lunark.moderation.service.IAccountReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reports/accounts")
public class AccountReportController {
    @Autowired
    private IAccountReportService accountReportService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountReportResponseDto> getAccountReport(@PathVariable("id") Long id) {
        return accountReportService.getById(id)
                .map(accountReport -> ResponseEntity.ok(modelMapper.map(accountReport, AccountReportResponseDto.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountReportResponseDto>> getAll() {
        List<AccountReport> accountReports = accountReportService.getAll();
        List<AccountReportResponseDto> reportResponseDtos = accountReports.stream()
                .map(report -> new AccountReportResponseDto(report.getId(), report.getDate(), report.getReporter().getId(), report.getReported().getId()))
                .collect(Collectors.toList());

        return accountReports.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(reportResponseDtos, HttpStatus.OK );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountReportResponseDto> block(@PathVariable Long id) {
        accountReportService.block(accountReportService.getById(id).get().getReported().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

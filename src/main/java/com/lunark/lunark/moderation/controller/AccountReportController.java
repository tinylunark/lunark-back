package com.lunark.lunark.moderation.controller;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.mapper.AccountReportDtoMapper;
import com.lunark.lunark.moderation.dto.AccountReportRequestDto;
import com.lunark.lunark.moderation.dto.AccountReportResponseDto;
import com.lunark.lunark.moderation.dto.HostReportEligibilityDto;
import com.lunark.lunark.moderation.model.AccountReport;
import com.lunark.lunark.moderation.service.IAccountReportService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    AccountReportDtoMapper accountReportDtoMapper;

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
                .map(report -> accountReportDtoMapper.toDto(report))
                .collect(Collectors.toList());

        return accountReports.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(reportResponseDtos, HttpStatus.OK );
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GUEST') or hasAuthority('HOST')")
    public ResponseEntity<AccountReportResponseDto> createReport(@Valid @RequestBody AccountReportRequestDto reportRequestDto) throws ConstraintViolationException {
        Account reporter = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountReport report;
        try {
            report = accountReportDtoMapper.toAccountReport(reportRequestDto, reporter);
            report = this.accountReportService.create(report);
            return new ResponseEntity<>(accountReportDtoMapper.toDto(report), HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountReportResponseDto> block(@PathVariable Long id) {
        accountReportService.block(accountReportService.getById(id).get().getReported().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/host-report-eligibility/{hostId}")
    @PreAuthorize("hasAuthority('GUEST')")
    public ResponseEntity<HostReportEligibilityDto> isCurrentGuestEligibleToReport(@PathVariable Long hostId) {
        Account reporter = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            boolean eligible = accountReportService.isGuestEligibleToReport(reporter, hostId);
            return new ResponseEntity<>(new HostReportEligibilityDto(hostId, eligible), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

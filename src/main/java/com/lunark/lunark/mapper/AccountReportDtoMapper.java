package com.lunark.lunark.mapper;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.moderation.dto.AccountReportRequestDto;
import com.lunark.lunark.moderation.dto.AccountReportResponseDto;
import com.lunark.lunark.moderation.model.AccountReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Optional;

@Component
public class AccountReportDtoMapper {
    private final IAccountRepository accountRepository;

    @Autowired
    public AccountReportDtoMapper(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountReport toAccountReport(AccountReportRequestDto dto, Account reporter) {
        Optional<Account> reported = accountRepository.findById(dto.reportedId());
        if (reported.isEmpty()) {
            throw new RuntimeException("Reported id is not valid");
        }

        return new AccountReport(null, dto.date(), reporter, reported.get(), dto.reason());
    }

    public AccountReportResponseDto toDto(AccountReport report) {
        return new AccountReportResponseDto(report.getId(), report.getDate(), report.getReporter().getId(), report.getReported().getId(), report.getReason());
    }
}

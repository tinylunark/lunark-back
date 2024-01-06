package com.lunark.lunark.moderation.service;

import com.lunark.lunark.moderation.model.AccountReport;

import java.util.List;
import java.util.Optional;

public interface IAccountReportService {
    Optional<AccountReport> getById(Long id);
    List<AccountReport> getAll();
    void block(Long id);
}

package com.lunark.lunark.moderation.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.auth.service.IAccountService;
import com.lunark.lunark.moderation.model.AccountReport;
import com.lunark.lunark.moderation.repository.IAccountReportRepository;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountReportService implements IAccountReportService {


    @Autowired
    private IAccountReportRepository accountReportRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IReservationService reservationService;

    @Override
    public Optional<AccountReport> getById(Long id) {
        return accountReportRepository.findById(id);
    }

    @Override
    public List<AccountReport> getAll() {
        return accountReportRepository.findAll();
    }

    @Override
    public void block(Long id) {
        Optional<Account> optionalAccount = accountService.find(id);
        if(optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            blockAccount(account);
            cancelAllResevations(reservationService.getAllReservationsForUser(account.getId()));
            removeAccountReportsForBlockedAccount(account);
        }
    }

    private void blockAccount(Account account) {
        account.setBlocked(true);
        accountService.saveAndFlush(account);
    }

    private void cancelAllResevations(List<Reservation> reservationList) {
        accountService.cancelAllReservations(reservationList);
    }

    private void removeAccountReportsForBlockedAccount(Account account) {
        List<AccountReport> reportsToRemove = findReportsByReportedEmail(account.getEmail());
        if (!reportsToRemove.isEmpty()) {
            accountReportRepository.deleteAll(reportsToRemove);
        }
    }

    private List<AccountReport> findReportsByReportedEmail(String email) {
        return accountReportRepository.findByReportedEmail(email);
    }
}

package com.lunark.lunark.moderation.repository;

import com.lunark.lunark.moderation.model.AccountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface IAccountReportRepository extends JpaRepository<AccountReport, Long> {
    Optional<AccountReport> findById(Long id);
    @Query("SELECT ar FROM AccountReport ar WHERE ar.reported.email = :email")
    List<AccountReport> findByReportedEmail(@Param("email") String email);
}

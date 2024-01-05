package com.lunark.lunark.moderation.repository;

import com.lunark.lunark.moderation.model.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IReviewReportRepository extends JpaRepository<ReviewReport, Long> {
    Optional<ReviewReport> findById(Long id);
}

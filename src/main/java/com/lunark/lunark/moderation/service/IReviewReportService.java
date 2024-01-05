package com.lunark.lunark.moderation.service;

import com.lunark.lunark.moderation.dto.ReviewReportRequestDto;
import com.lunark.lunark.moderation.dto.ReviewReportResponseDto;
import com.lunark.lunark.moderation.model.ReviewReport;

import java.util.List;

public interface IReviewReportService {
    ReviewReport getById(Long id);
    List<ReviewReport> getAll();
    ReviewReport create(ReviewReportRequestDto dto);
    ReviewReport update(Long id, ReviewReportRequestDto dto);
    void delete(Long id);
}

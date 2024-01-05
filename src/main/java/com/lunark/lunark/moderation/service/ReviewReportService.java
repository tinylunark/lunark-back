package com.lunark.lunark.moderation.service;

import com.lunark.lunark.moderation.dto.ReviewReportRequestDto;
import com.lunark.lunark.moderation.dto.ReviewReportResponseDto;
import com.lunark.lunark.moderation.model.ReviewReport;
import com.lunark.lunark.moderation.repository.IReviewReportRepository;
import com.lunark.lunark.reviews.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewReportService implements IReviewReportService {

    @Autowired
    private IReviewReportRepository reviewReportRepository;

    @Autowired
    private IReviewRepository reviewRepository;

    @Override
    public ReviewReport getById(Long id) {
        return reviewReportRepository.findById(id).orElse(null);
    }

    @Override
    public List<ReviewReport> getAll() {
        return reviewReportRepository.findAll();
    }

    @Override
    public ReviewReport create(ReviewReport report) {
        return this.reviewReportRepository.saveAndFlush(report);
    }

    @Override
    public ReviewReport update(ReviewReport report) {
        return null;
    }

    @Override
    public void delete(Long id) {
        ReviewReport reviewReport = reviewReportRepository.findById(id).get();
        reviewRepository.deleteById(reviewReport.getReview().getId());
        reviewReportRepository.deleteById(id);
    }
}

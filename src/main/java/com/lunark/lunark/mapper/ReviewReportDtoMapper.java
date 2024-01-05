package com.lunark.lunark.mapper;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.auth.repository.IAccountRepository;
import com.lunark.lunark.moderation.dto.ReviewReportRequestDto;
import com.lunark.lunark.moderation.model.ReviewReport;
import com.lunark.lunark.reviews.model.Review;
import com.lunark.lunark.reviews.repository.IReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ReviewReportDtoMapper {
    private final ModelMapper modelMapper;
    private final IAccountRepository accountRepository;
    private final IReviewRepository reviewRepository;
    private final Clock clock;

    @Autowired
    public ReviewReportDtoMapper(ModelMapper modelMapper, IAccountRepository accountRepository, IReviewRepository reviewRepository, Clock clock) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.reviewRepository = reviewRepository;
        this.clock = clock;
    }

    public Optional<ReviewReport> toReviewReport(ReviewReportRequestDto dto) {
        Optional<Account> reporter = this.accountRepository.findById(dto.reporterId());
        Optional<Review> review = this.reviewRepository.findById(dto.reviewId());
        if (reporter.isEmpty() || review.isEmpty()) {
            return null;
        }
        return Optional.of(new ReviewReport(dto.date(), reporter.get(), review.get()));
    }
}

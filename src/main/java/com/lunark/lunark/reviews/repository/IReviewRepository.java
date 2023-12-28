package com.lunark.lunark.reviews.repository;

import com.lunark.lunark.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review, Long> {
}

package com.lunark.lunark.reviews.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface IReviewService<Review> {
    Collection<Review> findAll();
    Optional<Review> find(Long id);
    Review create(Review review);
    Review createPropertyReview(Review review, Long propertyId);
    Review createHostReview(Review review, Long propertyId);
    Review update(Review review);
    void delete(Long id);
    boolean guestEligibleToReivew(Long guestId, Long propertyId);
}

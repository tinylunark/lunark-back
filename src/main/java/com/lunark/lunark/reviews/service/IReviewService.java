package com.lunark.lunark.reviews.service;

import com.lunark.lunark.reviews.model.Review;

import java.util.Collection;
import java.util.Optional;

public interface IReviewService<Review> {
    Collection<Review> findAll();
    Optional<Review> find(Long id);
    Review create(Review review);
    Review createPropertyReview(Review review, Long propertyId);
    Review createHostReview(Review review, Long hostId);
    Review update(Review review);
    void delete(Long id);
    boolean guestEligibleToReviewProperty(Long guestId, Long propertyId);
    boolean guestEligibleToReviewHost(Long guestId, Long hostId);

    Collection<com.lunark.lunark.reviews.model.Review> findAllUnapproved();

    com.lunark.lunark.reviews.model.Review approveReview(com.lunark.lunark.reviews.model.Review review);
}

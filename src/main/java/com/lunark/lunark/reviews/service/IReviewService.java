package com.lunark.lunark.reviews.service;

import java.util.Collection;
import java.util.Optional;

public interface IReviewService<Review> {
    Collection<Review> findAll();
    Optional<Review> find(Long id);
    Review create(Review review);
    Review update(Review review);
    void delete(Long id);
    boolean guestEligibleToReivew(Long guestId, Long propertyId);
}

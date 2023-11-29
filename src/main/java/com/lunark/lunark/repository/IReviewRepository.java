package com.lunark.lunark.repository;

import java.util.Collection;
import java.util.Optional;

public interface IReviewRepository<Review> {
    Collection<Review> findAll();
    Review create(Review review);
    Optional<Review> find(Long id);
    Review update(Review review);
    void delete(Long id);
}

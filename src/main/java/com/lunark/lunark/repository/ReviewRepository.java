package com.lunark.lunark.repository;

import com.lunark.lunark.model.Review;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReviewRepository implements IReviewRepository<Review> {

    private ConcurrentMap<Long, Review> reviews = new ConcurrentHashMap<Long, Review>();
    private static AtomicLong counter = new AtomicLong();

    @Override
    public Collection<Review> findAll() {
        return this.reviews.values();
    }

    @Override
    public Review create(Review review) {
        Long id = generateId(review.getId());
        review.setId(id);
        save(id, review);
        return review;
    }

    private Long generateId(Long id) {
        return (id != null) ? id : counter.incrementAndGet();
    }
    private void save(Long id, Review review) {
        this.reviews.put(id, review);
    }

    @Override
    public Optional<Review> find(Long id) {
        return Optional.ofNullable(this.reviews.get(id));
    }

    @Override
    public Review update(Review review) {
        Long id = review.getId();
        if( id != null ) this.reviews.put(id, review);
        return review;
    }

    @Override
    public void delete(Long id) {
        this.reviews.remove(id);
    }
}

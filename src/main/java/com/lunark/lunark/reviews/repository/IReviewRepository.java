package com.lunark.lunark.reviews.repository;

import com.lunark.lunark.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Account a join a.reviews r where a.id = ?1 and r.author.id = ?2")
    Optional<Review> findHostReviewByGuest(Long hostId, Long guestId);
    @Query("select r from Property p join p.reviews r where p.id = ?1 and r.approved = true")
    Collection<Review> findApprovedReviewsForProperty(Long id);
    @Query("select r from Account a join a.reviews r where a.id = ?1 and r.approved = true")
    Collection<Review> findApprovedReviewsForHost(Long id);
}

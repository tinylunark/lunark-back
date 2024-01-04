package com.lunark.lunark.moderation.model;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.reviews.model.Review;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_report")
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Account reporter;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    // Private constructor
    private ReviewReport(Long id, LocalDateTime date, Account reporter, Review review) {
        this.id = id;
        this.date = date;
        this.reporter = reporter;
        this.review = review;
    }

    // Static factory method to create instances
    public static ReviewReport createReviewReport(Long id, LocalDateTime date, Account reporter, Review review) {
        return new ReviewReport(id, date, reporter, review);
    }

    // No-args constructor for JPA
    protected ReviewReport() {
    }

    // Getters for fields
    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Account getReporter() {
        return reporter;
    }

    public Review getReview() {
        return review;
    }
}
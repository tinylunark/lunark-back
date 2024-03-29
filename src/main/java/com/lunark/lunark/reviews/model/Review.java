package com.lunark.lunark.reviews.model;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@SQLDelete(sql
        = "UPDATE review "
        + "SET deleted = true "
        + "WHERE id = ?")
@Where(clause = "deleted = false")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String description;
    private boolean approved = false;

    private LocalDateTime date;
    private ReviewType type;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account author;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name="account_reviews",
            joinColumns = {@JoinColumn(name = "reviews_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")}
    )
    private Account host;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_reviews",
            joinColumns = {@JoinColumn(name = "reviews_id")},
            inverseJoinColumns = {@JoinColumn(name = "property_id")}
    )
    private Property property;

    public Review() {

    }

    public enum ReviewType {
        HOST,
        PROPERTY
    }

    public Review(Long id, int rating, String desc, boolean approved, LocalDateTime date, ReviewType type) {
        this.id = id;
        this.rating = rating;
        this.description = desc;
        this.approved = approved;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ReviewType getType() {
        return type;
    }

    public void setType(ReviewType type) {
        this.type = type;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Account getHost() {
        if (this.type != ReviewType.HOST) {
            throw new IllegalStateException("Cannot get host for non-host reviews");
        }
        return host;
    }

    public void setHost(Account host) {
        if (this.type != ReviewType.HOST) {
            throw new IllegalStateException("Cannot set host for non-host reviews");
        }
        this.host = host;
    }

    public Property getProperty() {
        if (this.type != ReviewType.PROPERTY) {
            throw new IllegalStateException("Cannot get property for non-property reviews");
        }
        return property;
    }

    public void setProperty(Property property) {
        if (this.type != ReviewType.PROPERTY) {
            throw new IllegalStateException("Cannot set property for non-property reviews");
        }
        this.property = property;
    }
}

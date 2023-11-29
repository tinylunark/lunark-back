package com.lunark.lunark.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Review {

    private Long id;
    private int rating;
    private String desc;
    private boolean approved;

    private LocalDateTime date;
    private ReviewType type;

    public enum ReviewType {
        HOST,
        PROPERTY
    }

    public Review(Long id, int rating, String desc, boolean approved, LocalDateTime date, ReviewType type) {
        this.id = id;
        this.rating = rating;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}

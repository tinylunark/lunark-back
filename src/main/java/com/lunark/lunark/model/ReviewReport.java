package com.lunark.lunark.model;

import java.time.LocalDateTime;

public class ReviewReport {
    private Long id;
    private LocalDateTime date;

    public ReviewReport(Long id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReviewReport{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}

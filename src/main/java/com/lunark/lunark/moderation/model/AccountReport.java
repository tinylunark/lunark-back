package com.lunark.lunark.moderation.model;

import com.lunark.lunark.auth.model.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_report")
public class AccountReport
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "date")
        private LocalDateTime date;
        @ManyToOne
        @JoinColumn(name = "reporter_id")
        private Account reporter;
        @ManyToOne
        @JoinColumn(name = "reported_id")
        private Account reported;
        @Column
        private String reason;

        public AccountReport() {
        }

        public AccountReport(Long id, LocalDateTime date, Account reporter, Account reported, String reason) {
                this.id = id;
                this.date = date;
                this.reporter = reporter;
                this.reported = reported;
                this.reason = reason;
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

        public Account getReporter() {
                return reporter;
        }

        public void setReporter(Account reporter) {
                this.reporter = reporter;
        }

        public Account getReported() {
                return reported;
        }

        public void setReported(Account reported) {
                this.reported = reported;
        }

        public String getReason() {
                return reason;
        }

        public void setReason(String reason) {
                this.reason = reason;
        }
}

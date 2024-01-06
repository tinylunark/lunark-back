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

        public AccountReport() {
        }

        public AccountReport(Long id, LocalDateTime date, Account reporter, Account reported) {
                this.id = id;
                this.date = date;
                this.reporter = reporter;
                this.reported = reported;
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
}

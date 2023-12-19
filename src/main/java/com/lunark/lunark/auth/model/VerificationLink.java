package com.lunark.lunark.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Duration;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class VerificationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private boolean used;
    @NotNull
    private Date created;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    public VerificationLink(Account account, Date created) {
        this.account = account;
        this.created = created;
        this.used = false;
    }
    
    public boolean isValid() {
        return !used && Duration.between(created.toInstant(), new Date().toInstant()).toDays() < 1;
    }

    public boolean tryUse() {
        if (isValid()) {
            account.verify();
            this.used = true;
            return true;
        }
        return false;
    }
}

package com.lunark.lunark.auth.model;

import java.time.Duration;
import java.util.Date;

public class VerificationLink {
    private int id;
    private boolean used;
    private Date created;
    private Account account;
    
    public boolean isValid() {
        return !used && Duration.between(created.toInstant(), new Date().toInstant()).toDays() < 1;
    }

    public boolean tryUse() {
        if (isValid()) {
            account.verify();
            return true;
        }
        return false;
    }
}

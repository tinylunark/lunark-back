package com.lunark.lunark.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

    public AuthRequest() {
        super();
    }

    public AuthRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

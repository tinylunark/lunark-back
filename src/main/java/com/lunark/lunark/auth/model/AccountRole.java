package com.lunark.lunark.auth.model;

public enum AccountRole {
    GUEST("guest"),
    HOST("host"),
    ADMIN("admin");

    private String name;

    AccountRole(String name) {
        this.name = name;
    }

    public static AccountRole fromString(String value) {
        switch (value) {
            case "guest":
                return AccountRole.GUEST;
            case "host":
                return AccountRole.HOST;
            case "admin":
                return AccountRole.ADMIN;
        }
        throw new IllegalArgumentException("Invalid string in account role");
    }

    @Override
    public String toString() {
        return this.name;
    }
}

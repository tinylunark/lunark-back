package com.lunark.lunark.model;

public enum AccountRole {
    GUEST,
    HOST,
    ADMIN;

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
}

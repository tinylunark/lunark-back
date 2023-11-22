package com.lunark.lunark.model;

public enum AccountRole {
    GUEST(0),
    HOST(1),
    ADMIN(2);

    private int value;

    AccountRole(int i) {
        this.value = i;
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
        switch (this.value) {
            case 0:
                return "guest";
            case 1:
                return "host";
            case 2:
                return "admin";
        }
        return "";
    }
}

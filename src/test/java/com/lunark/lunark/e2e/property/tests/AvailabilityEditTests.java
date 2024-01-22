package com.lunark.lunark.e2e.property.tests;

import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.property.pages.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AvailabilityEditTests extends TestBase {
    private final String email = "user2@example.com";
    private final String password = "password2";
    private final String URL = "http://localhost:4200";

    @BeforeEach
    public void setUp() {
        driver.get(URL);
    }

    public void logIn() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openLoginDialog();
        mainPage.logIn(email, password);
    }

    @Test
    public void testLogIn() {
        MainPage mainPage = new MainPage(driver);
        logIn();
        Assertions.assertTrue(mainPage.isLoggedInAsHost());
    }
}

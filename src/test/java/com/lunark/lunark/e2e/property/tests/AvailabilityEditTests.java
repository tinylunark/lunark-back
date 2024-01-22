package com.lunark.lunark.e2e.property.tests;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.property.pages.CalendarPage;
import com.lunark.lunark.e2e.property.pages.EditPropertyPage;
import com.lunark.lunark.e2e.property.pages.MainPage;
import com.lunark.lunark.e2e.property.pages.MyPropertiesPage;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TestConfiguration.class)
@Import(TestConfiguration.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AvailabilityEditTests extends TestBase {
    private final String email = "user2@example.com";
    private final String password = "password2";
    private final String URL = "http://localhost:4200";
    private final String propertyName = "Test1";

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        driver.get(URL);
        Instant testTime = LocalDate.of(2023, 11, 28).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Long testTimestamp = testTime.getEpochSecond() * 1000;
        String timeshiftjs = "";
        try (Scanner s = new Scanner(new File("timeshift.js")).useDelimiter("\\Z")) {
            timeshiftjs = s.next();
        }

        ((JavascriptExecutor) driver).executeScript(timeshiftjs);
        ((JavascriptExecutor) driver).executeScript("Date = TimeShift.Date;" +
                "TimeShift.setTime(arguments[0]);", testTimestamp);
    }

    @AfterEach
    public void tearDown() {
        MainPage mainPage = new MainPage(driver);
        mainPage.logOut();
    }

    public void logIn() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openHome();
        mainPage.openLoginDialog();
        mainPage.logIn(email, password);
    }

    @Test
    @Sql("classpath:test-data-availability.sql")
    public void testLogIn() {
        MainPage mainPage = new MainPage(driver);
        logIn();
        Assertions.assertTrue(mainPage.isLoggedInAsHost());
    }

    @Test
    @Sql("classpath:test-data-availability.sql")
    public void testOpenMyProperties() {
        MainPage mainPage = new MainPage(driver);
        logIn();
        mainPage.openMyProperties();
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        Assertions.assertTrue(myPropertiesPage.isLoaded());
    }

    @Test
    @Sql("classpath:test-data-availability.sql")
    public void testOpenEditProperty() {
        testOpenMyProperties();
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        Assertions.assertTrue(editPropertyPage.isLoaded());
        Assertions.assertEquals(propertyName, editPropertyPage.getPropertyName());
        editPropertyPage.openPriceTable();
    }

    @Test
    @Sql("classpath:test-data-availability.sql")
    public void testDeleteAll() throws InterruptedException {
        testOpenMyProperties();
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openCalendar();
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.deleteRange(LocalDate.of(2023, 11, 29), LocalDate.of(2024, 1, 23));
        editPropertyPage.openPriceTable();
        Thread.sleep(10000);
    }
}

package com.lunark.lunark.e2e.property.tests;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.property.pages.*;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.property.controller.PropertyControllerPricesAndAvailabilityIntegrationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TestConfiguration.class)
@Import(TestConfiguration.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AvailabilityEditTests extends TestBase {
    private final String URL = "http://localhost:4200";

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

    public void logIn(String email, String password) {
        MainPage mainPage = new MainPage(driver);
        mainPage.openHome();
        mainPage.openLoginDialog();
        mainPage.logIn(email, password);
    }

    @ParameterizedTest
    @CsvSource("user2@example.com,password2")
    @Sql("classpath:test-data-availability.sql")
    public void testLogIn(String email, String password) {
        MainPage mainPage = new MainPage(driver);
        logIn(email, password);
        Assertions.assertTrue(mainPage.isLoggedInAsHost());
    }

    @ParameterizedTest
    @CsvSource("user2@example.com,password2")
    @Sql("classpath:test-data-availability.sql")
    public void testOpenMyProperties(String email, String password) {
        MainPage mainPage = new MainPage(driver);
        logIn(email, password);
        mainPage.openMyProperties();
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        Assertions.assertTrue(myPropertiesPage.isLoaded());
    }

    @ParameterizedTest
    @CsvSource("user2@example.com,password2,Test1")
    @Sql("classpath:test-data-availability.sql")
    public void testOpenEditProperty(String email, String password, String propertyName) {
        testOpenMyProperties(email, password);
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        Assertions.assertTrue(editPropertyPage.isLoaded());
        Assertions.assertEquals(propertyName, editPropertyPage.getPropertyName());
        editPropertyPage.openPriceTable();
    }

    @ParameterizedTest
    @CsvSource("user2@example.com,password2,Test1")
    @Sql("classpath:test-data-availability.sql")
    public void testDeleteAllFrontendOnly(String email, String password, String propertyName) throws InterruptedException {
        testOpenMyProperties(email, password);
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openCalendar();
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.deleteRange(LocalDate.of(2023, 11, 29), LocalDate.of(2024, 1, 23));
        editPropertyPage.openPriceTable();
        PriceTablePage priceTablePage = new PriceTablePage(driver);
        Assertions.assertEquals(1, priceTablePage.getCurrentlyDisplayedRows().size());
    }

    public void clearCalendar(String email, String password, String propertyName) {
        testOpenMyProperties(email, password);
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openCalendar();
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.deleteRange(LocalDate.of(2023, 11, 29), LocalDate.of(2024, 1, 23));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPositiveCases")
    @Sql("classpath:test-data-availability.sql")
    public void testPositiveCases(String email, String password, String propertyName, List<PropertyAvailabilityEntry> availabilityEntries) {
        clearCalendar(email, password, propertyName);
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.addEntries(availabilityEntries);
        calendarPage.save();
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openPriceTable();
        PriceTablePage priceTablePage = new PriceTablePage(driver);
        List<PriceTablePage.AvailabilityTableRow> expectedRows = priceTablePage.convertAvailabilityEntriesToRows(availabilityEntries);
        List<PriceTablePage.AvailabilityTableRow> actualRows = priceTablePage.getCurrentlyDisplayedRows();
        Assertions.assertEquals(expectedRows.size(), actualRows.size());
        for (int i = 0; i < expectedRows.size(); i++) {
            Assertions.assertEquals(expectedRows.get(i), actualRows.get(i));
        }
    }

    private static List<List<PropertyAvailabilityEntry>> extractFromArgumentsLists(List<Arguments> argumentsLists) {
        List<List<PropertyAvailabilityEntry>> result = new ArrayList<>();
        for (Arguments arguments: argumentsLists) {
            result.add((List<PropertyAvailabilityEntry>)arguments.get()[0]);
        }
        return result;
    }
    private static List<Arguments> provideParamsForPositiveCases() {
        List<List<PropertyAvailabilityEntry>> positiveCases = extractFromArgumentsLists(PropertyControllerPricesAndAvailabilityIntegrationTests.getPositiveRequests());
        String username = "user2@example.com";
        String password = "password2";
        String propertyName = "Test one";
        List<Arguments> params = new ArrayList<>();

        for (List<PropertyAvailabilityEntry> positiveCase: positiveCases) {
            params.add(Arguments.arguments(username, password, propertyName, positiveCase));
        }

        return params;
    }
}

package com.lunark.lunark.e2e.property.tests;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.property.pages.*;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import com.lunark.lunark.property.controller.PropertyControllerPricesAndAvailabilityIntegrationTests;
import org.junit.jupiter.api.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TestConfiguration.class)
@Import(TestConfiguration.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AvailabilityEditTests extends TestBase {
    private final String URL = "http://localhost:4200";
    private static final List<PropertyAvailabilityEntry> originalAvailabilityEntries =
            new ArrayList<>(Arrays.asList(
                    new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
            ));

    private static final LocalDate testDate = LocalDate.of(2023, 11, 28);
    private List<GuestReservationsPage.ReservationDisplayDto> originalGuestReservations;
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        driver.get(URL);
        Instant testTime = testDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Long testTimestamp = testTime.getEpochSecond() * 1000;
        String timeshiftjs = "";
        try (Scanner s = new Scanner(new File("timeshift.js")).useDelimiter("\\Z")) {
            timeshiftjs = s.next();
        }

        ((JavascriptExecutor) driver).executeScript(timeshiftjs);
        ((JavascriptExecutor) driver).executeScript("Date = TimeShift.Date;" +
                "TimeShift.setTime(arguments[0]);", testTimestamp);

        originalGuestReservations = getGuestReservations();
        Assertions.assertEquals("ACCEPTED", originalGuestReservations.get(0).getStatus());
        Assertions.assertEquals("PENDING", originalGuestReservations.get(1).getStatus());
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
    @CsvSource("user2@example.com,password2,Test one")
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
    @CsvSource("user2@example.com,password2,Test one")
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

    public void openCalendar(String email, String password, String propertyName) {
        testOpenMyProperties(email, password);
        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openCalendar();
    }

    public void clearCalendar(String email, String password, String propertyName) {
        openCalendar(email, password, propertyName);
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.deleteRange(LocalDate.of(2023, 11, 29), LocalDate.of(2026, 1, 23));
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

        assertThatRowsMatchEntries(availabilityEntries);
        verifyReservationsNotChanged();
    }

    private static List<Arguments> provideParamsForInvalidEntries() {
        String username = "user2@example.com";
        String password = "password2";
        String propertyName = "Test one";
        List<Arguments> params = List.of(
                Arguments.arguments(username, password, propertyName, new PropertyAvailabilityEntry(LocalDate.of(2024, 8, 4), -124), "Please fill in all fields", Operation.ADD),
                Arguments.arguments(username, password, propertyName, new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 3000), "You cannot add availability for past dates ❌", Operation.EDIT),
                Arguments.arguments(username, password, propertyName, new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 129078), "You cannot delete availability for past dates ❌", Operation.REMOVE),
                Arguments.arguments(username, password, propertyName, new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 1), 3000), "You cannot add availability for past dates ❌", Operation.ADD),
                Arguments.arguments(username, password, propertyName, new PropertyAvailabilityEntry(testDate, 129078), "You cannot add availability for past dates ❌", Operation.ADD),
                Arguments.arguments(username, password, propertyName, new PropertyAvailabilityEntry(testDate, 129078), "You cannot delete availability for past dates ❌", Operation.REMOVE)
        );

        return params;
    }

    @ParameterizedTest
    @MethodSource("provideParamsForInvalidEntries")
    @DisplayName("Will display error message when trying to do an invalid operation and won't change availability")
    @Sql("classpath:test-data-availability.sql")
    public void testInvalidEntry(String email, String password, String propertyName, PropertyAvailabilityEntry entry, String expectedMessage, Operation operation) {
        openCalendar(email, password, propertyName);
        CalendarPage calendarPage = new CalendarPage(driver);
        String errorMessage;
        switch (operation) {
            case ADD:
            case EDIT:
                errorMessage = calendarPage.addInvalidEntry(entry);
                break;
            case REMOVE:
                errorMessage = calendarPage.invalidRemove(entry);
                break;
            default:
                errorMessage = "";
                break;
        }
        Assertions.assertEquals(expectedMessage, errorMessage);

        calendarPage.save();

        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openPriceTable();

        assertThatRowsMatchEntries(originalAvailabilityEntries);
        verifyReservationsNotChanged();
    }

    private static List<Arguments> provideParamsForSaveErrors() {
        String username = "user2@example.com";
        String password = "password2";
        String propertyName = "Test one";
        List<PropertyAvailabilityEntry> removedReservedDay = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        List<PropertyAvailabilityEntry> changedPriceOnReservedDay = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 23509, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        List<PropertyAvailabilityEntry> originalEntriesForThree = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 28), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 29), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 30), 2000, null)
        ));
        List<Arguments> params = List.of(
                Arguments.arguments(username, password, propertyName, removedReservedDay, "You have closed the property for a date on which there is a reservation.", originalAvailabilityEntries),
                Arguments.arguments(username, password, "Test three", new ArrayList<PropertyAvailabilityEntry>(), "One of the fields you have edited is not valid.", originalEntriesForThree),
                Arguments.arguments(username, password, propertyName, changedPriceOnReservedDay, "You have closed the property for a date on which there is a reservation.", originalAvailabilityEntries)
        );

        return params;
    }

    @ParameterizedTest
    @MethodSource("provideParamsForSaveErrors")
    @DisplayName("Will display error message when changing availability so it conflicts with reservations or when deleting all available days. Property availability won't be changed.")
    @Sql("classpath:test-data-availability.sql")
    public void testErrorOnSave(String email, String password, String propertyName, List<PropertyAvailabilityEntry> availabilityEntries, String expectedMessage, List<PropertyAvailabilityEntry> originalAvailabilityEntries) {
        clearCalendar(email, password, propertyName);
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.addEntries(availabilityEntries);
        String errorMessage = calendarPage.causeSaveError();
        Assertions.assertEquals(expectedMessage, errorMessage);

        MainPage mainPage = new MainPage(driver);
        mainPage.openMyProperties();

        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openPriceTable();

        assertThatRowsMatchEntries(originalAvailabilityEntries);
        verifyReservationsNotChanged();
    }

    @Test
    @DisplayName("All pending reservations on a day the property was closed are rejected")
    @Sql("classpath:test-data-availability.sql")
    public void testClosePropertyWhenThereIsPendingReservation() {
        String username = "user2@example.com";
        String password = "password2";
        String propertyName = "Test three";
        openCalendar(username, password, propertyName);
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.deleteRange(LocalDate.of(2024, 6, 29), LocalDate.of(2024, 6, 30));
        calendarPage.save();
        List<PropertyAvailabilityEntry> entriesAfterDeletion = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 28), 2000, null)
        ));

        MyPropertiesPage myPropertiesPage = new MyPropertiesPage(driver);
        myPropertiesPage.editProperty(propertyName);
        EditPropertyPage editPropertyPage = new EditPropertyPage(driver);
        editPropertyPage.openPriceTable();

        assertThatRowsMatchEntries(entriesAfterDeletion);

        MainPage mainPage = new MainPage(driver);
        mainPage.logOut();
        List<GuestReservationsPage.ReservationDisplayDto> changedReservations = getGuestReservations();
        Assertions.assertEquals(originalGuestReservations.get(0), changedReservations.get(0));
        Assertions.assertNotEquals(originalGuestReservations.get(1), changedReservations.get(1));
        Assertions.assertEquals("REJECTED", changedReservations.get(1).getStatus());
    }

    private void assertThatRowsMatchEntries(List<PropertyAvailabilityEntry> availabilityEntries) {
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

    private List<GuestReservationsPage.ReservationDisplayDto> getGuestReservations() {
        logIn("user1@example.com", "password1");
        MainPage mainPage = new MainPage(driver);
        mainPage.openReservations();
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        List<GuestReservationsPage.ReservationDisplayDto> reservations = guestReservationsPage.getReservations();
        mainPage.logOut();
        return reservations;
    }

    private void verifyReservationsNotChanged() {
        MainPage mainPage = new MainPage(driver);
        mainPage.logOut();
        List<GuestReservationsPage.ReservationDisplayDto> newReservations = getGuestReservations();
        Assertions.assertEquals(originalGuestReservations.size(), newReservations.size());
        for (int i = 0; i < originalGuestReservations.size(); i++) {
            Assertions.assertEquals(originalGuestReservations.get(i), newReservations.get(i));
        }
    }

    enum Operation {
        ADD, EDIT, REMOVE;
    }
}

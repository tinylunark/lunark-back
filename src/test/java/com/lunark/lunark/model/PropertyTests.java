package com.lunark.lunark.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.Image;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PropertyTests {
    private Property property;

    @BeforeEach
    public void setUp() {
        Instant testTime = LocalDate.of(2023, 11, 28).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Clock testClock = Clock.fixed(testTime, ZoneId.systemDefault());
        property =  new Property(1L,
            "Vila Golija",
            1,
            5,
            "Vila pored Semeteskog jezera",
            45,
            45,
            new Address(),
            new ArrayList<PropertyImage>(),
            true,
            PricingMode.WHOLE_UNIT,
            24,
            true,
            new ArrayList<Review>(),
            new ArrayList<PropertyAvailabilityEntry>(),
                List.of(new Amenity(1L, "Wi-Fi", null)),
            testClock
        );

        List<PropertyAvailabilityEntry> availabilityEntries = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, property),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, property),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, property),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, property),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, property),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, property, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, property, true)
        ));


        property.setAvailabilityEntries(availabilityEntries);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2023-12-01, 2023-12-01, true",
            "2023-12-01, 2023-12-03, true",
            "2023-12-01, 2023-12-04, false",
            "2023-11-30, 2023-12-04, false",
            "2023-11-30, 2023-12-03, false",
            "2023-12-01, 2023-12-09, false",
            "2024-12-01, 2024-12-09, false",
            "2024-12-11, 2024-12-11, false",
            "2024-12-11, 2024-12-12, false",
            "2024-12-09, 2024-12-12, false",
            "2024-12-13, 2024-12-14, false",
            "2024-12-12, 2024-12-14, false",
    })
    public void testIsAvailable(LocalDate from, LocalDate to, boolean shouldBeAvailable) {
        Assertions.assertEquals(shouldBeAvailable, property.isAvailable(from, to));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2023-12-01, 2023-12-01, true",
            "2023-12-01, 2023-12-03, true",
            "2023-12-01, 2023-12-04, false",
            "2023-11-30, 2023-12-04, false",
            "2023-11-30, 2023-12-03, false",
            "2023-12-01, 2023-12-09, false",
            "2024-12-01, 2024-12-09, false",
    })
    public void testReserve(LocalDate from, LocalDate to, boolean shouldReserve) {
        Assertions.assertEquals(shouldReserve, property.reserve(from, to, 3).isPresent());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2023-12-01, false",
            "2023-12-03, false",
            "2023-12-02, false",
            "2023-12-04, true",
            "2023-12-08, true",
    })
    public void testMakeAvailable(LocalDate day, boolean shouldWork) {
        Assertions.assertEquals(shouldWork, property.makeAvailable(day, 10000));
    }


    public static List<Arguments> returnParamsForTestMakeAvailable() {
        List<PropertyAvailabilityEntry> validAvailabilityEntries = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 4), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 5), 1000, null)
        ));
        List<PropertyAvailabilityEntry> validAvailabilityEntriesWithGap = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 4), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 8), 1000, null)
        ));
        List<PropertyAvailabilityEntry> entriesThatAlreadyExist = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 1000, null)
        ));
        List<PropertyAvailabilityEntry> onlyOneEntryExists = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 4), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 7), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 1000, null)
        ));
        return Arrays.asList(
                Arguments.arguments(validAvailabilityEntries, true),
                Arguments.arguments(validAvailabilityEntriesWithGap, true),
                Arguments.arguments(entriesThatAlreadyExist, false),
                Arguments.arguments(onlyOneEntryExists, false)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "returnParamsForTestMakeAvailable")
    public void testMakeAvailableMultipleDays(Collection<PropertyAvailabilityEntry> availabilityEntries, boolean shouldWork) {
        Assertions.assertEquals(shouldWork, property.makeAvailable(availabilityEntries));
    }

    public static List<Arguments> returnParamsForTestSetAvailabilityEntries() {
        List<PropertyAvailabilityEntry> addedNewDaysInMiddle = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 4), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 5), 1000, null)
        ));

        List<PropertyAvailabilityEntry> addedNewDaysBefore = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 30), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 29), 1000, null)
        ));

        List<PropertyAvailabilityEntry> addedNewDaysAfter = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 13), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 14), 1000, null)
        ));

        List<PropertyAvailabilityEntry> changedPrices = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 13), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 14), 1000, null)
        ));
        List<PropertyAvailabilityEntry> removedReservedDay = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        List<PropertyAvailabilityEntry> priceChangedOnReservedDay = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 3000, null, true)
        ));

        List<PropertyAvailabilityEntry> removedAvailableDays = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));

        return Arrays.asList(
                Arguments.arguments(addedNewDaysBefore, true),
                Arguments.arguments(addedNewDaysAfter, true),
                Arguments.arguments(addedNewDaysInMiddle, true),
                Arguments.arguments(removedReservedDay, false),
                Arguments.arguments(priceChangedOnReservedDay, false),
                Arguments.arguments(removedAvailableDays, true)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "returnParamsForTestSetAvailabilityEntries")
    public void testSetAvailabilityEntries(Collection<PropertyAvailabilityEntry> newAvailabilityEntries, boolean shouldWork) {
        Assertions.assertEquals(shouldWork, property.setAvailabilityEntries(newAvailabilityEntries));
    }
}

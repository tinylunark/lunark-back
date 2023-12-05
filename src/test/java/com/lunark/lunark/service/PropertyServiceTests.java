package com.lunark.lunark.service;

import com.lunark.lunark.model.*;
import com.lunark.lunark.repository.IPropertyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.time.*;
import java.util.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTests {
    @Mock
    private IPropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private List<Property> properties;

    private List<PropertyAvailabilityEntry> availabilityEntries;

    @BeforeEach
    void setUp() {
        Instant testTime = LocalDate.of(2023, 11, 28).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Clock testClock = Clock.fixed(testTime, ZoneId.systemDefault());

        MockitoAnnotations.initMocks(this);
        availabilityEntries = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        properties = new ArrayList<>(Arrays.asList(
                new Property(0L,
                        "Vila Golija",
                        1,
                        5,
                        "Vila pored Semeteskog jezera",
                        43.2970419, 20.719494,
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
                ),
                new Property(1L,
                        "Ami Hotel",
                        1,
                        5,
                        "Lorem ipsum",
                        45.2600297, 19.8310925,
                        new Address(),
                        new ArrayList<PropertyImage>(),
                        true,
                        PricingMode.WHOLE_UNIT,
                        24,
                        true,
                        new ArrayList<Review>(),
                        new ArrayList<>(),
                        List.of(new Amenity(1L, "Wi-Fi", null)),
                        testClock
                ),
                new Property(2L,
                        "Hotel Terasa",
                        1,
                        5,
                        "Lorem ipsum",
                        45.2576875, 19.84215,
                        new Address(),
                        new ArrayList<PropertyImage>(),
                        true,
                        PricingMode.WHOLE_UNIT,
                        24,
                        true,
                        new ArrayList<Review>(),
                        availabilityEntries,
                        List.of(new Amenity(1L, "Wi-Fi", null)),
                        testClock
                )
        ));
    }

    @Test
    public void testFindAll() {
        Mockito.when(propertyRepository.findAll()).thenReturn(properties);
        Assertions.assertSame(properties, propertyService.findAll());
        Mockito.verify(propertyRepository).findAll();
    }

    public static List<Arguments> returnParamsForTestChangePricesAndAvailability() {
        List<PropertyAvailabilityEntry> addedDaysFromEmpty = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 4), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 5), 1000, null)
        ));

        List<PropertyAvailabilityEntry> addedNewDaysInMiddle = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
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
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
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
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
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
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
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
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        List<PropertyAvailabilityEntry> priceChangedOnReservedDay = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 3000, null, true)
        ));

        List<PropertyAvailabilityEntry> removedAvailableDays = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));

        List<PropertyAvailabilityEntry> madeAvailableInThePast = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 27), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));

        List<PropertyAvailabilityEntry> madeAvailableToday = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 28), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 3000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));

        return Arrays.asList(
                Arguments.arguments(0L, addedDaysFromEmpty, true),
                Arguments.arguments(2L, addedNewDaysBefore, true),
                Arguments.arguments(2L, addedNewDaysAfter, true),
                Arguments.arguments(2L, addedNewDaysInMiddle, true),
                Arguments.arguments(2L, removedReservedDay, false),
                Arguments.arguments(2L, priceChangedOnReservedDay, false),
                Arguments.arguments(2L, removedAvailableDays, true),
                Arguments.arguments(3L, addedNewDaysAfter, false), // Non-existent property
                Arguments.arguments(2L, madeAvailableInThePast, false),
                Arguments.arguments(2L, madeAvailableToday, false)
        );

    }
    @ParameterizedTest
    @MethodSource(value = "returnParamsForTestChangePricesAndAvailability")
    public void testChangePricesAndAvailability(Long id, Collection<PropertyAvailabilityEntry> newPricesAndAvailability, boolean shouldWork) {
        if (id < this.properties.size()){
            Mockito.when(propertyRepository.findById(id)).thenReturn(Optional.ofNullable(properties.get(id.intValue())));
        } else {
            Mockito.when(propertyRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        }

        if(shouldWork) {
            Mockito.when(propertyRepository.saveAndFlush(properties.get(id.intValue()))).thenReturn(properties.get(id.intValue()));
        }

        Assertions.assertEquals(shouldWork, propertyService.changePricesAndAvailability(id, newPricesAndAvailability));
        Mockito.verify(propertyRepository).findById(id);
        if(shouldWork) {
            for(PropertyAvailabilityEntry propertyAvailabilityEntry: newPricesAndAvailability) {
                Assertions.assertEquals(properties.get(id.intValue()), propertyAvailabilityEntry.getProperty());
            }
            Mockito.verify(propertyRepository).saveAndFlush(properties.get(id.intValue()));
        }
    }
}

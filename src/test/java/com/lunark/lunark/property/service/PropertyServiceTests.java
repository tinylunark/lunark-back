package com.lunark.lunark.property.service;

import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.*;
import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import com.lunark.lunark.properties.service.PropertyService;
import com.lunark.lunark.reservations.service.IReservationService;
import com.lunark.lunark.reviews.model.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PropertyServiceTests {
    @MockBean
    private IPropertyRepository propertyRepository;
    @MockBean
    private IReservationService reservationService;

    @InjectMocks
    private PropertyService propertyService;

    private List<Property> properties;

    private List<PropertyAvailabilityEntry> availabilityEntries;

    @BeforeEach
    void setUp() {
        Instant testTime = LocalDate.of(2023, 11, 28).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Clock testClock = Clock.fixed(testTime, ZoneId.systemDefault());
        availabilityEntries = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
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
                        new ArrayList<>(List.of(new Amenity(1L, "Wi-Fi", null))),
                        testClock,
                        Property.PropertyType.ROOM,
                        new Account()
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
                        new ArrayList<>(List.of(new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 28), 1000, null))),
                        new ArrayList<>(List.of(new Amenity(1L, "Wi-Fi", null))),
                        testClock,
                        Property.PropertyType.ROOM,
                        new Account()
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
                        new ArrayList<>(List.of(new Amenity(1L, "Wi-Fi", null))),
                        testClock,
                        Property.PropertyType.ROOM,
                        new Account()
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
        List<LocalDate> removedDays = new ArrayList<>(Arrays.asList(
                LocalDate.of(2023, 12, 3),
                LocalDate.of(2023, 12, 9),
                LocalDate.of(2023, 12, 10)
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

        List<PropertyAvailabilityEntry> removedDayInThePast = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        List<PropertyAvailabilityEntry> duplicateDates = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 1), 1000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 6000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 6000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
        ));
        List<PropertyAvailabilityEntry> changedPriceInThePast = new ArrayList<>(Arrays.asList(
                new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 7000, null),
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
        List<PropertyAvailabilityEntry> changedPriceToday =
                new ArrayList<>(List.of(new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 28), 7000, null)));
        List<PropertyAvailabilityEntry> removedToday = new ArrayList<>();
        List<LocalDate> noClosedDays = new ArrayList<>();
        return Arrays.asList(
                Arguments.arguments(0L, addedDaysFromEmpty, true, noClosedDays),
                Arguments.arguments(2L, addedNewDaysBefore, true, noClosedDays),
                Arguments.arguments(2L, addedNewDaysAfter, true, noClosedDays),
                Arguments.arguments(2L, addedNewDaysInMiddle, true, noClosedDays),
                Arguments.arguments(2L, removedReservedDay, false, noClosedDays),
                Arguments.arguments(2L, priceChangedOnReservedDay, false, noClosedDays),
                Arguments.arguments(2L, removedAvailableDays, true, removedDays),
                Arguments.arguments(2L, madeAvailableInThePast, false, noClosedDays),
                Arguments.arguments(2L, madeAvailableToday, false, noClosedDays),
                Arguments.arguments(2L, removedDayInThePast, false, noClosedDays),
                Arguments.arguments(2L, duplicateDates, false, noClosedDays),
                Arguments.arguments(2L, changedPriceInThePast, false, noClosedDays),
                Arguments.arguments(1L, changedPriceToday, false, noClosedDays),
                Arguments.arguments(1L, removedToday, false, noClosedDays)
        );
    }
    @ParameterizedTest
    @MethodSource(value = "returnParamsForTestChangePricesAndAvailability")
    public void testChangePricesAndAvailability(Long id, Collection<PropertyAvailabilityEntry> newPricesAndAvailability, boolean shouldWork, List<LocalDate> closedDays) {
        if (id < this.properties.size()){
            Mockito.when(propertyRepository.findById(id)).thenReturn(Optional.ofNullable(properties.get(id.intValue())));
        } else {
            Mockito.when(propertyRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        }

        if(shouldWork) {
            Mockito.when(propertyRepository.save(properties.get(id.intValue()))).thenReturn(properties.get(id.intValue()));
        }

        Property propertyWithChangedAvailability = new Property();
        propertyWithChangedAvailability.setId(id);
        propertyWithChangedAvailability.getAvailabilityEntries().addAll(newPricesAndAvailability);

        if (!shouldWork) {
            Assertions.assertThrowsExactly(RuntimeException.class, () -> propertyService.update(propertyWithChangedAvailability, id), "Conflict between new and old availability entries");
            Assertions.assertNotEquals(newPricesAndAvailability, properties.get(id.intValue()).getAvailabilityEntries());
            Mockito.verify(propertyRepository, Mockito.only()).findById(id);
            Mockito.verifyNoMoreInteractions(propertyRepository);
            Mockito.verifyNoInteractions(reservationService);
            return;
        }

        propertyService.update(propertyWithChangedAvailability, id);
        Assertions.assertEquals(newPricesAndAvailability, properties.get(id.intValue()).getAvailabilityEntries());
        Mockito.verify(propertyRepository, Mockito.atMostOnce()).findById(id);
        Mockito.verify(propertyRepository).save(properties.get(id.intValue()));
        Mockito.verify(propertyRepository).flush();
        Mockito.verifyNoMoreInteractions(propertyRepository);
        for (LocalDate closedDay: closedDays) {
            Mockito.verify(reservationService).rejectAllPendingReservationsAtPropertyThatContainDate(Mockito.eq(id), Mockito.eq(closedDay));
            System.out.println(closedDay);
        }
        Mockito.verifyNoMoreInteractions(reservationService);
    }
}

package com.lunark.lunark.property.controller;

import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.mapper.PropertyDtoMapper;
import com.lunark.lunark.properties.dto.AvailabilityEntryDto;
import com.lunark.lunark.properties.dto.PropertyRequestDto;
import com.lunark.lunark.properties.dto.PropertyResponseDto;
import com.lunark.lunark.properties.model.*;
import com.lunark.lunark.reservations.repository.IReservationRepository;
import com.lunark.lunark.reviews.model.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
@Import(TestConfiguration.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:test-data-availability.sql")
public class PropertyControllerPricesAndAvailabilityIntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IReservationRepository reservationRepository;

    private final String fakeHostJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMkBleGFtcGxlLmNvbSIsInJvbGUiOlt7ImF1dGhvcml0eSI6IkhPU1QifV0sInByb2ZpbGVJZCI6MiwiZXhwIjoxNzAzMTU4NTQwMCwiaWF0IjoxNzAzMTQwNTQwfQ.4NJKcQ1CcU1c9fQ-CaQr18vCsOwg-H74TrGVWL2H4YjuBYNAjUx5_uQu_nMDe68yLn3KqTsqIETKoOuXQqEJqg";
    private List<PropertyAvailabilityEntry> originalAvailabilityEntries =
            new ArrayList<>(Arrays.asList(
                    new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                    new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
            ));

    private Property property =
            new Property(1337L,
                    "Hotel Ami",
                    1,
                    5,
                    "Lorem ipsum",
                    45.2600297, 19.8310925,
                    new Address("street", "city", "country"),
                    new ArrayList<PropertyImage>(),
                    true,
                    PricingMode.WHOLE_UNIT,
                    24,
                    true,
                    new ArrayList<Review>(),
                    originalAvailabilityEntries,
                    new ArrayList<>(List.of(new Amenity(1L, "Wi-Fi", null))),
                    null,
                    Property.PropertyType.ROOM,
                    new Account(10L, null, null, null, null, null, null, true, null, false, false, null, null)
            );

    @BeforeEach
    public void setUp() {
        originalAvailabilityEntries =
                new ArrayList<>(Arrays.asList(
                        new PropertyAvailabilityEntry(LocalDate.of(2022, 12, 1), 1000, null),
                        new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 2), 2000, null),
                        new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 3), 2000, null),
                        new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 9), 2000, null),
                        new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 10), 2000, null),
                        new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 11), 2000, null, true),
                        new PropertyAvailabilityEntry(LocalDate.of(2023, 12, 12), 2000, null, true)
                ));

        property =
                new Property(1337L,
                        "Hotel Ami",
                        1,
                        5,
                        "Lorem ipsum",
                        45.2600297, 19.8310925,
                        new Address("street", "city", "country"),
                        new ArrayList<PropertyImage>(),
                        true,
                        PricingMode.WHOLE_UNIT,
                        24,
                        true,
                        new ArrayList<Review>(),
                        originalAvailabilityEntries,
                        new ArrayList<>(List.of(new Amenity(1L, "Wi-Fi", null))),
                        null,
                        Property.PropertyType.ROOM,
                        new Account(10L, null, null, null, null, null, null, true, null, false, false, null, null)
                );

    }

    public static List<Arguments> getPositiveRequests() {
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

        return Arrays.asList(
                Arguments.arguments(addedNewDaysBefore, new ArrayList<LocalDate>()),
                Arguments.arguments(addedNewDaysAfter, new ArrayList<LocalDate>()),
                Arguments.arguments(changedPrices, new ArrayList<LocalDate>()),
                Arguments.arguments(addedNewDaysInMiddle, new ArrayList<LocalDate>()),
                Arguments.arguments(removedAvailableDays, removedDays)
        );
    }

    private HttpEntity<PropertyRequestDto> getHttpEntity(List<PropertyAvailabilityEntry> availabilityEntries) {
        property.getAvailabilityEntries().clear();
        property.getAvailabilityEntries().addAll(availabilityEntries);
        PropertyRequestDto requestDto = PropertyDtoMapper.fromPropertyToRequestDto(property);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + fakeHostJWT);
        return new HttpEntity<>(requestDto, headers);
    }

    @ParameterizedTest
    @MethodSource(value = "getPositiveRequests")
    @DisplayName("Should add new dates")
    public void testAddAvailabilitySuccessful(List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries, List<LocalDate> removedDays) {
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntity(newPropertyAvailabilityEntries);
        ResponseEntity<PropertyResponseDto> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropertyResponseDto response = responseEntity.getBody();
        List<AvailabilityEntryDto> changedAvailabilityEntries = response.getAvailabilityEntries().stream().toList();
        Assertions.assertEquals(newPropertyAvailabilityEntries.size(), changedAvailabilityEntries.size());
        for (int i = 0; i < changedAvailabilityEntries.size(); i++) {
            Assertions.assertEquals(newPropertyAvailabilityEntries.get(i).getPrice(), changedAvailabilityEntries.get(i).getPrice());
            Assertions.assertEquals(newPropertyAvailabilityEntries.get(i).getDate(), changedAvailabilityEntries.get(i).getDate());
            Assertions.assertEquals(newPropertyAvailabilityEntries.get(i).isReserved(), changedAvailabilityEntries.get(i).isReserved());
        }
        for (LocalDate removedDay: removedDays) {
            Assertions.assertEquals(0, reservationRepository.findAllPendingReservationsAtPropertyThatContainDate(property.getId(), removedDay).size());
        }
    }

    public static List<Arguments> getNegativeRequests() {

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
        return Arrays.asList(
                Arguments.arguments(removedReservedDay),
                Arguments.arguments(priceChangedOnReservedDay),
                Arguments.arguments(madeAvailableInThePast),
                Arguments.arguments(madeAvailableToday),
                Arguments.arguments(removedDayInThePast),
                Arguments.arguments(duplicateDates),
                Arguments.arguments(changedPriceInThePast)
        );
    }
    @ParameterizedTest
    @MethodSource(value = "getNegativeRequests")
    @DisplayName("Should not add new dates")
    public void testChangeAvailabilityConflict(List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries) {
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntity(newPropertyAvailabilityEntries);
        ResponseEntity<PropertyResponseDto> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    public static List<Arguments> provideParamsForToday() {
        List<PropertyAvailabilityEntry> changedPriceToday =
                new ArrayList<>(List.of(new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 28), 7000, null)));
        List<PropertyAvailabilityEntry> removedAvailabilityToday =
                new ArrayList<>(List.of(new PropertyAvailabilityEntry(LocalDate.of(2023, 11, 29), 7000, null)));
        return Arrays.asList(
                Arguments.arguments(changedPriceToday),
                Arguments.arguments(removedAvailabilityToday)
        );
    }
    @ParameterizedTest
    @MethodSource("provideParamsForToday")
    @DisplayName("Won't change availability for today")
    public void testChangeAvailabilityToday(List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries) {
        property.setId(1338L);
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntity(newPropertyAvailabilityEntries);
        ResponseEntity<PropertyResponseDto> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
    @Test
    @DisplayName("Won't remove all entries")
    public void testRemoveAllEntries() {
        List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries = new ArrayList<>();
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntity(newPropertyAvailabilityEntries);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private HttpEntity<PropertyRequestDto> getHttpEntityWithoutHeaders(List<PropertyAvailabilityEntry> availabilityEntries) {
        property.getAvailabilityEntries().clear();
        property.getAvailabilityEntries().addAll(availabilityEntries);
        PropertyRequestDto requestDto = PropertyDtoMapper.fromPropertyToRequestDto(property);
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(requestDto, headers);
    }

    @Test
    @DisplayName("Unregistered users can't change availability of properties")
    public void testUnauthorized() {
        List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries = originalAvailabilityEntries.stream().toList();
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntityWithoutHeaders(newPropertyAvailabilityEntries);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Will remove one day and reject pending reservations")
    public void testRemoveDay() {
        property.setId(1339L);
        List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries = new ArrayList<>(List.of(
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 28), 7000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 29), 1000, null)
        ));
        Assertions.assertEquals(1, reservationRepository.findAllPendingReservationsAtPropertyThatContainDate(1339L, LocalDate.of(2024, 6, 29)).size());
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntity(newPropertyAvailabilityEntries);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(0, reservationRepository.findAllPendingReservationsAtPropertyThatContainDate(1339L, LocalDate.of(2024, 6, 29)).size());
    }
    @Test
    @DisplayName("Will change cancellation deadline and pricing mode")
    public void testChangeCancellationDeadlineAndPricingMode() {
        property.setId(1339L);
        List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries = new ArrayList<>(List.of(
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 28), 7000, null),
                new PropertyAvailabilityEntry(LocalDate.of(2024, 6, 29), 1000, null)
        ));
        property.setPricingMode(PricingMode.PER_PERSON);
        property.setCancellationDeadline(88);
        HttpEntity<PropertyRequestDto> httpEntity = getHttpEntity(newPropertyAvailabilityEntries);
        ResponseEntity<PropertyResponseDto> responseEntity = testRestTemplate.exchange("/api/properties",
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropertyResponseDto responseDto = responseEntity.getBody();
        Assertions.assertEquals(PricingMode.PER_PERSON, responseDto.getPricingMode());
        Assertions.assertEquals(88, responseDto.getCancellationDeadline());
    }
}

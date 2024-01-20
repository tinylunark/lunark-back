package com.lunark.lunark.property.controller;

import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.mapper.PropertyDtoMapper;
import com.lunark.lunark.properties.dto.AvailabilityEntryDto;
import com.lunark.lunark.properties.dto.PropertyRequestDto;
import com.lunark.lunark.properties.dto.PropertyResponseDto;
import com.lunark.lunark.properties.model.*;
import com.lunark.lunark.reviews.model.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.stream.Collectors;

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
    private void setUp() {
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

        return Arrays.asList(
                Arguments.arguments(addedNewDaysBefore),
                Arguments.arguments(addedNewDaysAfter),
                Arguments.arguments(changedPrices),
                Arguments.arguments(addedNewDaysInMiddle),
                Arguments.arguments(removedAvailableDays)
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
    public void testAddAvailabilitySuccessful(List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries) {
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
    }

    @ParameterizedTest
    @MethodSource(value = "getPositiveRequests")
    @DisplayName("Should not add new dates")
    public void testAddAvailabilityConfilict(List<PropertyAvailabilityEntry> newPropertyAvailabilityEntries) {
        // TODO: Negative tests
    }
}

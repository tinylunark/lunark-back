package com.lunark.lunark.reservation.controller;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.dto.ReservationResponseDto;
import com.lunark.lunark.reservations.model.ReservationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
@Import(TestConfiguration.class)
@ActiveProfiles("test")
@Sql("classpath:reservation-creation-test-data.sql")
public class ReservationControllerIntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String FAKE_GUEST_JWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbSIsInJvbGUiOlt7ImF1dGhvcml0eSI6IkdVRVNUIn1dLCJwcm9maWxlSWQiOjEsImV4cCI6MTcwMzU5OTQ5OTAsImlhdCI6MTcwMzU4MTQ5OX0.NktxTWijd8RyZbiYaaoNIaOc43eFqBYgD9fJwS-jOEhs2V9qOOoP2IFUPBbszZWuuUgGv9G3PnuDO8kEIDbTbQ";

    private static final String URL = "/api/reservations";

    private HttpEntity<ReservationRequestDto> generateHttpEntity(ReservationRequestDto dto) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(FAKE_GUEST_JWT);

        return new HttpEntity<>(dto, headers);
    }

    private <T> ResponseEntity<T> executePostRequest(HttpEntity<ReservationRequestDto> httpEntity, Class<T> responseType) {
        return testRestTemplate.postForEntity(URL, httpEntity, responseType);
    }

    @Test
    @DisplayName("Should create a reservation with valid input")
    public void shouldCreateReservation() {

        var dto = new ReservationRequestDto(
                1L,
                LocalDate.of(2024, 12, 12),
                LocalDate.of(2024, 12, 13),
                2
        );
        var http = generateHttpEntity(dto);

        ResponseEntity<ReservationResponseDto> responseEntity = executePostRequest(http, ReservationResponseDto.class);
        ReservationResponseDto response = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(response.getProperty().isAutoApproveEnabled() ? ReservationStatus.ACCEPTED : ReservationStatus.PENDING, response.getStatus());
        Assertions.assertEquals(1L, response.getProperty().getId());
        Assertions.assertEquals(4000.0, response.getPrice());
        Assertions.assertEquals(LocalDate.of(2024, 12, 12), response.getStartDate());
        Assertions.assertEquals(LocalDate.of(2024, 12, 13), response.getEndDate());
        Assertions.assertEquals(2, response.getNumberOfGuests());
    }

    @Test
    @DisplayName("Should return 404 if property does not exist")
    public void shouldReturn404IfNoProperty() {
        var dto = new ReservationRequestDto(
                99999999L,
                LocalDate.of(2024, 12, 12),
                LocalDate.of(2024, 12, 13),
                2
        );
        var http = generateHttpEntity(dto);

        ResponseEntity<String> responseEntity = executePostRequest(http, String.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody().contains("property not found"));
    }

    @Test
    @DisplayName("Should return 401 if the user is not authorized")
    public void shouldReturn401IfNotAuthorized() {
        var dto = new ReservationRequestDto(
                1L,
                LocalDate.of(2024, 12, 12),
                LocalDate.of(2024, 12, 13),
                2
        );
        var headers = new HttpHeaders();
        var http = new HttpEntity<ReservationRequestDto>(dto, headers);

        ResponseEntity<String> responseEntity = executePostRequest(http, String.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("getDtosWithInvalidDateRanges")
    @DisplayName("Should return 400 if the property is not available for a given date range")
    public void shouldReturn400IfNotAvailable(ReservationRequestDto dto) {
        var http = generateHttpEntity(dto);

        ResponseEntity<String> responseEntity = executePostRequest(http, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody().contains("property not available for given date range"));
    }

    @ParameterizedTest
    @MethodSource("getDtosWithInvalidGuestNumbers")
    @DisplayName("Should return 400 if the given guest number is not in defined range")
    public void shouldReturn400IfWrongGuestNumber(ReservationRequestDto dto) {
        var http = generateHttpEntity(dto);

        ResponseEntity<String> responseEntity = executePostRequest(http, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody().contains("number of guests does not match the defined guest range of property"));
    }

    private static List<ReservationRequestDto> getDtosWithInvalidDateRanges() {
        return List.of(
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 12), LocalDate.of(2024, 12, 14), 2),
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 13), 2),
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 12), LocalDate.of(2024, 12, 20), 2),
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 14), LocalDate.of(2024, 12, 20), 2),
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 5), LocalDate.of(2024, 12, 7), 2),
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 18), LocalDate.of(2024, 12, 20), 2)
        );
    }

    private static List<ReservationRequestDto> getDtosWithInvalidGuestNumbers() {
        return List.of(
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 12), LocalDate.of(2024, 12, 13), 1),
                new ReservationRequestDto(1L, LocalDate.of(2024, 12, 12), LocalDate.of(2024, 12, 13), 5)
        );
    }
}

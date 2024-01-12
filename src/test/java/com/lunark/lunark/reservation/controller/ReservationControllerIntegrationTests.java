package com.lunark.lunark.reservation.controller;

import com.lunark.lunark.reservations.dto.ReservationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private String mockJWTToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMkBleGFtcGxlLmNvbSIsInJvbGUiOlt7ImF1dGhvcml0eSI6IkhPU1QifV0sInByb2ZpbGVJZCI6MiwiZXhwIjoxNzAzMTU4NTQwMCwiaWF0IjoxNzAzMTQwNTQwfQ.4NJKcQ1CcU1c9fQ-CaQr18vCsOwg-H74TrGVWL2H4YjuBYNAjUx5_uQu_nMDe68yLn3KqTsqIETKoOuXQqEJqg";
    private HttpEntity<?> getHttpEntityWithAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(mockJWTToken);
        return new HttpEntity<>(headers);
    }

    @Test
    @DisplayName("Should Get Incoming Reservations for Specific Host")
    @Sql("classpath:test-reservation-acceptance.sql")
    public void testGetIncomingReservationsForSpecificHost() {
        Long hostId = 2L;

        ResponseEntity<List<ReservationDto>> responseEntity = restTemplate.exchange(
                "/api/reservations/incoming-reservations?hostId=" + hostId,
                HttpMethod.GET,
                getHttpEntityWithAuthHeader(),
                new ParameterizedTypeReference<List<ReservationDto>>() {}
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<ReservationDto> reservations = responseEntity.getBody();
        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());
    }

    @Test
    @DisplayName("Test Successful Reservation Acceptance")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testAcceptReservationSuccess() {
        Long validReservationId = 6L;

        ReservationDto reservation = new ReservationDto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(mockJWTToken);

        HttpEntity<ReservationDto> entity = new HttpEntity<>(reservation, headers);

        ResponseEntity<ReservationDto> response = restTemplate.exchange(
                "/api/reservations/accept/" + validReservationId,
                HttpMethod.POST,
                entity,
                ReservationDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Test Reservation Not Found")
    @Sql("classpath:test-reservation-acceptance.sql")
    public void testAcceptReservationNotFound() {
        Long invalidReservationId = 999L;
        ResponseEntity<ReservationDto> response = restTemplate.exchange(
                "/accept/" + invalidReservationId,
                HttpMethod.POST,
                getHttpEntityWithAuthHeader(),
                ReservationDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test Invalid Reservation ID")
    @Sql("classpath:test-reservation-acceptance.sql")
    public void testAcceptReservationInvalidId() {
        String invalidId = "invalid";
        ResponseEntity<ReservationDto> response = restTemplate.exchange(
                "/accept/" + invalidId,
                HttpMethod.POST,
                getHttpEntityWithAuthHeader(),
                ReservationDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
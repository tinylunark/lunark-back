package com.lunark.lunark.reservation.controller;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.reservations.dto.ReservationRequestDto;
import com.lunark.lunark.reservations.dto.ReservationResponseDto;
import com.lunark.lunark.reservations.model.ReservationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
@Import(TestConfiguration.class)
@ActiveProfiles("test")
@Sql("classpath:test-data-availability.sql")
public class ReservationControllerIntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String fakeGuestJwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbSIsInJvbGUiOlt7ImF1dGhvcml0eSI6IkdVRVNUIn1dLCJwcm9maWxlSWQiOjEsImV4cCI6MTcwMzU5OTQ5OTAsImlhdCI6MTcwMzU4MTQ5OX0.NktxTWijd8RyZbiYaaoNIaOc43eFqBYgD9fJwS-jOEhs2V9qOOoP2IFUPBbszZWuuUgGv9G3PnuDO8kEIDbTbQ";

    @Test
    @DisplayName("Should create a reservation with valid input")
    public void shouldCreateReservation() {

        var dto = new ReservationRequestDto(
                1L,
                LocalDate.of(2024, 12, 12),
                LocalDate.of(2024, 12, 13),
                2
        );

        var headers = new HttpHeaders();
        headers.setBearerAuth(fakeGuestJwt);
        var http = new HttpEntity<ReservationRequestDto>(dto, headers);

        ResponseEntity<ReservationResponseDto> responseEntity = testRestTemplate.postForEntity(
                "/api/reservations",
                http,
                ReservationResponseDto.class
        );

        System.out.println("Response body: " + responseEntity.getBody());
        System.out.println("Response code: " + responseEntity.getStatusCode());

        ReservationResponseDto response = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(response.getProperty().isAutoApproveEnabled() ? ReservationStatus.ACCEPTED : ReservationStatus.PENDING, response.getStatus());
        Assertions.assertEquals(1L, response.getProperty().getId());
    }
}

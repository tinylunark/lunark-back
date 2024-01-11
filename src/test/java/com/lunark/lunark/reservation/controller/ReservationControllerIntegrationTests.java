package com.lunark.lunark.reservation.controller;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reservations.controller.ReservationController;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reservations.model.ReservationStatus;
import com.lunark.lunark.reservations.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // Import this
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @WithMockUser(authorities = "HOST")
    public void testGetIncomingReservations() throws Exception {
        Long hostId = 1L;

        List<Reservation> mockReservations = Arrays.asList(
                new Reservation(1L, LocalDate.of(2024, 5, 26), LocalDate.of(2024, 5, 29), 3, ReservationStatus.PENDING, 10000, new Property(), new Account())
        );

        when(reservationService.getIncomingReservationsForHostId(hostId)).thenReturn(mockReservations);

        mockMvc.perform(get("/api/reservations/incoming-reservations")
                        .param("hostId", String.valueOf(hostId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(jsonPath("$").isArray())
                        .andExpect(jsonPath("$.length()").value(mockReservations.size()))
        ;
    }

}

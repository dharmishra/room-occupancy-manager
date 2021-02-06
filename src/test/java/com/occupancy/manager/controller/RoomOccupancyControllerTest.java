package com.occupancy.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.occupancy.manager.domain.RoomOccupancyRequest;
import com.occupancy.manager.model.RoomOccupancy;
import com.occupancy.manager.service.RoomOccupancyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RoomOccupancyController.class)
class RoomOccupancyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomOccupancyService roomOccupancyService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenRoomAvailability_thenReturnRoomOccupancy() throws Exception {
        RoomOccupancyRequest request = new RoomOccupancyRequest();
        request.setAvailableEconomyRooms(3);
        request.setAvailablePremiumRooms(3);

        List<Integer> paymentList = List.of(23, 45, 155, 374, 22, 99, 100, 101, 115, 209);

        RoomOccupancy expectedRoomOccupancy = RoomOccupancy.builder()
                .totalPaymentMade(paymentList)
                .premiumRoomAvailable(3)
                .economyRoomAvailable(3)
                .premiumRoomTotalMade(738)
                .economyRoomTotalMade(167)
                .economyRoomOccupied(3)
                .premiumRoomOccupied(3)
                .build();
        given(roomOccupancyService.occupyRooms(any())).willReturn(expectedRoomOccupancy);

        mockMvc.perform(post("/room-occupancy/api/room-occupancy")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
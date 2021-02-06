package com.occupancy.manager.service;

import com.occupancy.manager.domain.RoomOccupancyRequest;
import com.occupancy.manager.model.RoomOccupancy;
import com.occupancy.manager.repository.RoomOccupancyRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomOccupancyServiceTest {

    private final RoomOccupancyRepository repository = new RoomOccupancyRepository();

    private RoomOccupancyService service = new RoomOccupancyService(repository);

    @Test
    public void shouldReturnValidRoomOccupancy() {
        int expectedPremiumTotal = 738;
        int expectedEconomyTotal = 167;
        int expectedPremiumRoomOccupied = 3;
        int expectedEconomyRoomOccupied = 3;

        RoomOccupancyRequest roomOccupancyRequest = new RoomOccupancyRequest();
        roomOccupancyRequest.setAvailablePremiumRooms(3);
        roomOccupancyRequest.setAvailableEconomyRooms(3);

        RoomOccupancy roomOccupancy = service.occupyRooms(roomOccupancyRequest);
        assertEquals(expectedEconomyRoomOccupied, roomOccupancy.getEconomyRoomOccupied());
        assertEquals(expectedEconomyTotal, roomOccupancy.getEconomyRoomTotalMade());
        assertEquals(expectedPremiumRoomOccupied, roomOccupancy.getPremiumRoomOccupied());
        assertEquals(expectedPremiumTotal, roomOccupancy.getPremiumRoomTotalMade());
    }

    @Test
    public void shouldReturnZeroForRoomOccupancy() {
        int expectedPremiumTotal = 0;
        int expectedEconomyTotal = 0;
        int expectedPremiumRoomOccupied = 0;
        int expectedEconomyRoomOccupied = 0;

        RoomOccupancyRequest roomOccupancyRequest = new RoomOccupancyRequest();
        roomOccupancyRequest.setAvailablePremiumRooms(0);
        roomOccupancyRequest.setAvailableEconomyRooms(0);

        RoomOccupancy roomOccupancy = service.occupyRooms(roomOccupancyRequest);
        assertEquals(expectedEconomyRoomOccupied, roomOccupancy.getEconomyRoomOccupied());
        assertEquals(expectedEconomyTotal, roomOccupancy.getEconomyRoomTotalMade());
        assertEquals(expectedPremiumRoomOccupied, roomOccupancy.getPremiumRoomOccupied());
        assertEquals(expectedPremiumTotal, roomOccupancy.getPremiumRoomTotalMade());
    }
}
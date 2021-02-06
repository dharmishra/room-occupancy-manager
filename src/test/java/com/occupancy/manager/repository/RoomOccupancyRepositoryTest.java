package com.occupancy.manager.repository;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomOccupancyRepositoryTest {

    private final RoomOccupancyRepository repository = new RoomOccupancyRepository();

    @Test
    public void testRoomPaymentData() {
        List<Integer> result = repository.getRoomPayments();
        assertNotNull(result);
        Integer[] test = {
                23,
                45,
                155,
                374,
                22,
                99,
                100,
                101,
                115,
                209
        };
        assertArrayEquals(test, result.toArray());
    }
}
package com.occupancy.manager.model;

import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoomOccupancy {

    private int economyRoomOccupied;
    private int premiumRoomOccupied;
    private int economyRoomAvailable;
    private int premiumRoomAvailable;
    private int economyRoomTotalMade;
    private int premiumRoomTotalMade;
    private List<Integer> totalPaymentMade;
}

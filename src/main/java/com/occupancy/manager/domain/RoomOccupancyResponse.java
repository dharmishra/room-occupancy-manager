package com.occupancy.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoomOccupancyResponse {

    private int totalUsedPremiumRooms;
    private int totalPremiumRoomsEarnings;
    private int totalUsedEconomyRooms;
    private int totalEconomyRoomsEarnings;
}

package com.occupancy.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class RoomOccupancyRequest {

    @NotBlank
    @Min(value = 0, message = "Available Premium rooms can not be less than 0")
    private int availablePremiumRooms;

    @NotBlank
    @Min(value = 0, message = "Available Economy rooms can not be less than 0")
    private int availableEconomyRooms;
}

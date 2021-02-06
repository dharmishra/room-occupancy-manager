package com.occupancy.manager.controller;

import com.occupancy.manager.domain.RoomOccupancyRequest;
import com.occupancy.manager.domain.RoomOccupancyResponse;
import com.occupancy.manager.model.RoomOccupancy;
import com.occupancy.manager.repository.RoomOccupancyRepository;
import com.occupancy.manager.service.RoomOccupancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("room-occupancy/api")
@CrossOrigin
public class RoomOccupancyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomOccupancyController.class);

    RoomOccupancyService roomOccupancyService;
    RoomOccupancyRepository roomOccupancyRepository;

    @Autowired
    public RoomOccupancyController(RoomOccupancyService roomOccupancyService, RoomOccupancyRepository roomOccupancyRepository) {
        this.roomOccupancyService = roomOccupancyService;
        this.roomOccupancyRepository = roomOccupancyRepository;
    }

    @Operation(summary = "Api returns the Room occupancy status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room occupancy status",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = RoomOccupancyResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Illegal Argument provided")
    })
    @PostMapping("/room-occupancy")
    public RoomOccupancyResponse getRoomOccupancy(@Valid @RequestBody RoomOccupancyRequest roomOccupancyRequest) {
        LOGGER.info("Received Room Occupancy Request "  + roomOccupancyRequest);
        return getResponse(roomOccupancyRequest);
    }

    @Operation(summary = "Api returns the Room occupancy status for bulk room occupancy request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room occupancy status"),
            @ApiResponse(responseCode = "400", description = "Illegal Argument provided")
    })
    @PostMapping("/occupancy/bulk")
    public List<RoomOccupancyResponse> getRoomOccupancy(@Valid @RequestBody List<RoomOccupancyRequest> roomOccupancyRequests) {

        List<RoomOccupancyResponse> respOccupancyList = new ArrayList<>();

        roomOccupancyRequests.forEach((or) -> {
            respOccupancyList.add(getResponse(or));
        });
        return respOccupancyList;

    }

    public RoomOccupancyResponse getResponse(RoomOccupancyRequest roomOccupancyRequest) {
        RoomOccupancyResponse response = new RoomOccupancyResponse();

        LOGGER.info("Total Available Premium rooms: " + roomOccupancyRequest.getAvailablePremiumRooms());
        LOGGER.info("Total Available Economy rooms: " + roomOccupancyRequest.getAvailableEconomyRooms());

        List<Integer> totalPayments = roomOccupancyRepository.getRoomPayments();
        RoomOccupancy roomOccupancy = RoomOccupancy.builder()
                .totalPaymentMade(totalPayments)
                .economyRoomAvailable(roomOccupancyRequest.getAvailableEconomyRooms())
                .premiumRoomAvailable(roomOccupancyRequest.getAvailablePremiumRooms())
                .build();
        roomOccupancy = roomOccupancyService.occupyRooms(roomOccupancy);

        LOGGER.info("Usage Premium: " + roomOccupancy.getPremiumRoomOccupied() + "(EUR " + roomOccupancy.getPremiumRoomTotalMade() + ")");
        LOGGER.info("Usage Economy: " + roomOccupancy.getEconomyRoomOccupied() + "(EUR " + roomOccupancy.getEconomyRoomTotalMade() + ")");

        response.setTotalUsedPremiumRooms(roomOccupancy.getPremiumRoomOccupied());
        response.setTotalPremiumRoomsEarnings(roomOccupancy.getPremiumRoomTotalMade());
        response.setTotalUsedEconomyRooms(roomOccupancy.getEconomyRoomOccupied());
        response.setTotalEconomyRoomsEarnings(roomOccupancy.getEconomyRoomTotalMade());

        return response;
    }

}

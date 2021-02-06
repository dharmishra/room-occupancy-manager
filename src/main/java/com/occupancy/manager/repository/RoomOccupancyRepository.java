package com.occupancy.manager.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomOccupancyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomOccupancyRepository.class);
    private static List<Integer> roomPayments = null;

    private static final ObjectMapper mapper = new ObjectMapper();

    public RoomOccupancyRepository() {
        initData();
    }

    private void initData() {
        LOGGER.info("Reading sample Data from resource directory /data/sample_hotel_guests.json");
        try (InputStream inputStream = getClass().getResourceAsStream("/data/sample_hotel_guests.json")) {
                roomPayments = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException io) {
            LOGGER.warn("Error Occurred while constructing payment from the provided json /data/sample_hotel_guests.json", io);
            roomPayments = new ArrayList<>();
        }
        LOGGER.info("Room prices retrieved from the provided json file" + roomPayments.toString());
    }

    public List<Integer> getRoomPayments() {
        if (roomPayments == null || roomPayments.isEmpty()) {
            initData();
        }
        return roomPayments;
    }
}

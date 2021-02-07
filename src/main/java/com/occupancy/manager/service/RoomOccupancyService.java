package com.occupancy.manager.service;

import com.occupancy.manager.domain.RoomOccupancyRequest;
import com.occupancy.manager.model.RoomOccupancy;
import com.occupancy.manager.repository.RoomOccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomOccupancyService {

    public RoomOccupancyService(RoomOccupancyRepository roomOccupancyRepository) {
        this.roomOccupancyRepository = roomOccupancyRepository;
    }

    @Autowired
    RoomOccupancyRepository roomOccupancyRepository;

    public RoomOccupancy occupyRooms(RoomOccupancyRequest roomOccupancyRequest) {

        List<Integer> totalPayments = roomOccupancyRepository.getRoomPayments();
        RoomOccupancy roomOccupancy = RoomOccupancy.builder()
                .totalPaymentMade(totalPayments)
                .economyRoomAvailable(roomOccupancyRequest.getAvailableEconomyRooms())
                .premiumRoomAvailable(roomOccupancyRequest.getAvailablePremiumRooms())
                .build();

        RoomOccupancy occupancy = roomOccupancy.toBuilder().build();

        List<Integer> economyRoomLists;
        List<Integer> premiumRoomLists = null;

        int availableEconomyRooms = roomOccupancy.getEconomyRoomAvailable();
        int availablePremiumRooms = roomOccupancy.getPremiumRoomAvailable();

        List<Integer> paymentList = roomOccupancy.getTotalPaymentMade();
        paymentList.sort(Comparator.reverseOrder());

        //Extract Economy Payment List in sorted order
        List<Integer> sortedEconomyPaymentList = paymentList.stream()
                .filter( i -> i < 100)
                .collect(Collectors.toList());

        //Extract Premium Payment List in sorted order
        List<Integer> sortedPremiumPaymentList = paymentList.stream()
                .filter( i -> i >= 100)
                .collect(Collectors.toList());

        if (availableEconomyRooms == 0) {
            economyRoomLists = new ArrayList<>();
            premiumRoomLists = paymentList.stream()
                    .limit(availablePremiumRooms)
                    .collect(Collectors.toList());
        } else if (availableEconomyRooms == 1 && availablePremiumRooms > sortedPremiumPaymentList.size()) {
            economyRoomLists = sortedEconomyPaymentList.stream()
                    .limit(availableEconomyRooms + (availablePremiumRooms - sortedPremiumPaymentList.size()))
                    .filter(i -> i < sortedEconomyPaymentList.get((availablePremiumRooms - sortedPremiumPaymentList.size()) - 1))
                    .collect(Collectors.toList());
            premiumRoomLists = paymentList.stream()
                    .limit(availablePremiumRooms)
                    .collect(Collectors.toList());
        } else {
            economyRoomLists = sortedEconomyPaymentList.stream()
                    .limit(availableEconomyRooms)
                    .collect(Collectors.toList());
            premiumRoomLists = sortedPremiumPaymentList.stream()
                    .limit(availablePremiumRooms)
                    .collect(Collectors.toList());
        }

        occupancy =    occupancy.toBuilder()
                .economyRoomOccupied(economyRoomLists.size())
                .premiumRoomOccupied(premiumRoomLists.size())
                .economyRoomTotalMade(economyRoomLists.stream().mapToInt(Integer::valueOf).sum())
                .premiumRoomTotalMade(premiumRoomLists.stream().mapToInt(Integer::valueOf).sum())
                .build();

        return occupancy;
    }
}

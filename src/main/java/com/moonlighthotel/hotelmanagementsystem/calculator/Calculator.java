package com.moonlighthotel.hotelmanagementsystem.calculator;

import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.Room;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class Calculator {

    public Integer calculateDays(RoomReservation roomReservation) {
        Instant startDate = roomReservation.getStartDate();
        Instant endDate = roomReservation.getEndDate();
        return (Integer) (int) Duration.between(startDate, endDate).toDays();
    }

    public Double calculatePrice(Room room, Integer days) {
        Double pricePerNight = room.getPrice();
        return days * pricePerNight;
    }
}

package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.calculator.Calculator;
import com.moonlighthotel.hotelmanagementsystem.enumeration.StatusType;
import com.moonlighthotel.hotelmanagementsystem.model.RoomReservation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class RoomReservationBuilder {

    @Autowired
    private final Calculator calculator;

    public RoomReservation build(RoomReservation roomReservation) {
        Integer days = calculator.calculateDays(roomReservation);
        Double price = calculator.calculatePrice(roomReservation.getRoom(), days);

        return RoomReservation.builder()
                .id(roomReservation.getId())
                .startDate(roomReservation.getStartDate())
                .endDate(roomReservation.getEndDate())
                .days(days)
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .typeBed(roomReservation.getTypeBed())
                .view(roomReservation.getView())
                .price(price)
                .created(Instant.now())
                .status(StatusType.NEW)
                .room(roomReservation.getRoom())
                .user(roomReservation.getUser())
                .build();
    }

    public RoomReservation build(Long rid, RoomReservation roomReservation) {
        Integer days = calculator.calculateDays(roomReservation);
        Double price = calculator.calculatePrice(roomReservation.getRoom(), days);

        return RoomReservation.builder()
                .id(rid)
                .startDate(roomReservation.getStartDate())
                .endDate(roomReservation.getEndDate())
                .days(days)
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .typeBed(roomReservation.getTypeBed())
                .view(roomReservation.getView())
                .price(price)
                .created(Instant.now())
                .status(roomReservation.getStatus())
                .room(roomReservation.getRoom())
                .user(roomReservation.getUser())
                .build();
    }
}

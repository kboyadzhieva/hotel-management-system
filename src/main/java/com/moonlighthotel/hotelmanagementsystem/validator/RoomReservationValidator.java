package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.model.Room;
import com.moonlighthotel.hotelmanagementsystem.model.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class RoomReservationValidator {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final RoomValidator roomValidator;

    @Autowired
    private final DateValidator dateValidator;

    public void validateRoomReservation(Long id, RoomReservation roomReservation) {
        Room foundRoom = roomService.findById(id);
        validateDates(roomReservation.getStartDate(), roomReservation.getEndDate());
        validateNumberOfGuests(foundRoom, roomReservation);
        roomValidator.validateAreThereAnyRoomsAvailable(foundRoom, roomReservation);
    }

    private void validateDates(Instant startDate, Instant endDate) {
        dateValidator.validateDates(startDate, endDate);
    }

    private void validateNumberOfGuests(Room room, RoomReservation roomReservation) {
        if (roomReservation.getAdults() > room.getPeople()) {
            throw new InvalidRequestException(
                    String.format("The number of guests should be equal or less than %s.", room.getPeople()), "adults");
        }
    }
}

package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.Room;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomRepository;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoomValidator {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    private final DateFormatter dateFormatter;

    public void validateRoom(Room room) {
        validateTitle(room.getId(), room);
    }

    public void validateRoomForUpdate(Long id, Room room) {
        validateRoomExists(id);
        validateTitle(id, room);
    }

    public void validateRoomExists(Long id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Room with id '%s' does not exist.", id)));
    }

    public void validateAreThereAnyRoomsAvailable(Room room, RoomReservation roomReservation) {
        Integer reservationsCount = roomReservationRepository.countTheReservationsForASpecificRoom(
                room.getId(), roomReservation.getStartDate(), roomReservation.getEndDate());
        String period = dateFormatter.formatPeriod(roomReservation);

        boolean AreThereAnyRoomsAvailable = AreThereAnyRoomsAvailable(room.getCount(), reservationsCount);

        if (!AreThereAnyRoomsAvailable) {
            throw new DuplicateRecordException(String.format("There is no available '%s' for the selected period: %s. " +
                    "Please, try another period or check for another room.", room.getTitle(), period), "room");
        }
    }

    private void validateTitle(Long id, Room room) {
        Room foundRoom = roomRepository.findByTitle(room.getTitle());

        if (foundRoom != null && !foundRoom.getId().equals(id)) {
            throw new DuplicateRecordException(
                    String.format("Room with title '%s' already exists.", room.getTitle()), "title");
        }
    }

    private boolean AreThereAnyRoomsAvailable(Integer roomCount, Integer reservationsCount) {
        return reservationsCount == null || roomCount > reservationsCount;
    }
}

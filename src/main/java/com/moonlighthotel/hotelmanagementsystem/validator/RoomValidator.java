package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.Room;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoomValidator {

    @Autowired
    private final RoomRepository roomRepository;

    public void validateRoom(Room room) {
        validateTitle(room.getId(), room);
    }

    public void validateRoomForUpdate(Long id, Room room) {
        verifyRoomExists(id);
        validateTitle(id, room);
    }

    private void validateTitle(Long id, Room room) {
        Room foundRoom = roomRepository.findByTitle(room.getTitle());

        if (foundRoom != null && !foundRoom.getId().equals(id)) {
            throw new DuplicateRecordException(
                    String.format("Room with title '%s' already exists.", room.getTitle()), "title");
        }
    }

    private void verifyRoomExists(Long id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Room with id '%s' does not exist.", id)));
    }
}

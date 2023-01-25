package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.RoomBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.Room;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomRepository;
import com.moonlighthotel.hotelmanagementsystem.service.RoomService;
import com.moonlighthotel.hotelmanagementsystem.validator.RoomValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomValidator roomValidator;

    @Autowired
    private final RoomBuilder roomBuilder;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Room with id %d does not exist", id)));
    }

    @Override
    public Room save(Room room) {
        roomValidator.validateRoom(room);
        Room builtRoom = roomBuilder.build(room);

        return roomRepository.save(builtRoom);
    }

    @Override
    public Room update(Long id, Room room) {
        roomValidator.validateRoomForUpdate(id, room);
        Room builtRoom = roomBuilder.buildUpdatedRoom(id, room);

        return roomRepository.save(builtRoom);
    }

    @Override
    public void deleteById(Long id) {
        Room foundRoom = findById(id);
        roomRepository.deleteById(foundRoom.getId());
    }
}

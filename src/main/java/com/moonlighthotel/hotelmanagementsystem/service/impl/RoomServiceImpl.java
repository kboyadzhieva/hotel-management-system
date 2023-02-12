package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.RoomBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.Room;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomRepository;
import com.moonlighthotel.hotelmanagementsystem.service.RoomService;
import com.moonlighthotel.hotelmanagementsystem.validator.QueryParametersValidator;
import com.moonlighthotel.hotelmanagementsystem.validator.RoomValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final DateFormatter dateFormatter;

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final QueryParametersValidator queryParametersValidator;

    @Autowired
    private final RoomValidator roomValidator;

    @Autowired
    private final RoomBuilder roomBuilder;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAllAvailableRooms(RoomFilter roomFilter) {
        Instant startDate = dateFormatter.stringToInstant(roomFilter.getStartDate());
        Instant endDate = dateFormatter.stringToInstant(roomFilter.getEndDate());

        return roomRepository.findAllAvailableRooms(startDate, endDate, roomFilter.getAdults(), roomFilter.getKids());
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

    public List<Room> findAll(RoomFilter roomFilter) {
        boolean hasParameters = queryParametersValidator.AreThereQueryParameters(roomFilter);

        if (!hasParameters) {
            return findAll();
        } else {
            return findAllAvailableRooms(roomFilter);
        }
    }
}

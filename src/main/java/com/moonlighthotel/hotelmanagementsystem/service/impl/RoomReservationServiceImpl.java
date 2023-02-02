package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.RoomReservationBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomReservationRepository;
import com.moonlighthotel.hotelmanagementsystem.service.RoomReservationService;
import com.moonlighthotel.hotelmanagementsystem.validator.RoomReservationValidator;
import com.moonlighthotel.hotelmanagementsystem.validator.RoomValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoomReservationServiceImpl implements RoomReservationService {

    @Autowired
    private final RoomReservationRepository roomReservationRepository;

    @Autowired
    private final RoomValidator roomValidator;

    @Autowired
    private final RoomReservationValidator roomReservationValidator;

    @Autowired
    private final RoomReservationBuilder roomReservationBuilder;

    @Override
    public List<RoomReservation> findAllByRoomId(Long id) {
        roomValidator.validateRoomExists(id);
        return roomReservationRepository.findAllByRoomId(id);
    }

    @Override
    public RoomReservation findById(Long id, Long rid) {
        roomValidator.validateRoomExists(id);
        return roomReservationRepository.findById(rid)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Room reservation with id %d does not exist.", rid)));
    }

    @Override
    public RoomReservation save(Long id, RoomReservation roomReservation) {
        roomReservationValidator.validateRoomReservation(id, roomReservation);
        RoomReservation builtRoomReservation = roomReservationBuilder.build(roomReservation);
        return roomReservationRepository.save(builtRoomReservation);
    }

    @Override
    public RoomReservation update(Long id, Long rid, RoomReservation roomReservation) {
        RoomReservation foundRoomReservation = findById(id, rid);
        roomReservationValidator.validateRoomReservation(id, roomReservation);
        RoomReservation builtRoomReservation = roomReservationBuilder
                .build(foundRoomReservation.getId(), roomReservation);
        return roomReservationRepository.save(builtRoomReservation);
    }

    @Override
    public void deleteById(Long id, Long rid) {
        RoomReservation foundRoomReservation = findById(id, rid);
        roomReservationRepository.deleteById(foundRoomReservation.getId());
    }

    @Override
    public RoomReservation summarize(Long id, RoomReservation roomReservation) {
        roomReservationValidator.validateRoomReservation(id, roomReservation);
        return roomReservationBuilder.build(roomReservation);
    }
}

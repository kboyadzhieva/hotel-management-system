package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;

import java.util.List;

public interface RoomReservationService {

    List<RoomReservation> findAllByRoomId(Long id);

    RoomReservation findById(Long id, Long rid);

    RoomReservation save(Long id, RoomReservation roomReservation);

    RoomReservation update(Long id, Long rid, RoomReservation roomReservation);

    void deleteById(Long id, Long rid);

    RoomReservation summarize(Long id, RoomReservation roomReservation);
}

package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.Room;

import java.util.List;

public interface RoomService {

    List<Room> findAll();

    List<Room> findAllAvailableRooms(RoomFilter roomFilter);

    Room findById(Long id);

    Room save(Room room);

    Room update(Long id, Room room);

    void deleteById(Long id);

    List<Room> findAll(RoomFilter roomFilter);
}

package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.model.Room;

import java.util.List;

public interface RoomService {

    List<Room> findAllAvailableRooms(RoomFilter roomFilter);

    Room findById(Long id);

    Room save(Room room);

    Room update(Long id, Room room);

    void deleteById(Long id);
}

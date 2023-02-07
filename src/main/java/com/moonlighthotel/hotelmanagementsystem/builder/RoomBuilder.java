package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.room.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomBuilder {

    public Room build(Room room) {
        return Room.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .roomImages(room.getRoomImages())
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .count(room.getCount())
                .build();
    }

    public Room buildUpdatedRoom(Long id, Room room) {
        return Room.builder()
                .id(id)
                .title(room.getTitle())
                .image(room.getImage())
                .roomImages(room.getRoomImages())
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .count(room.getCount())
                .build();
    }
}

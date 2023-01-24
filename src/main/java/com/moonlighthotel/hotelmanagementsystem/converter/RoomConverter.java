package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.model.Room;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoomConverter {

    @Autowired
    private final ImageConverter imageConverter;

    public Room toRoom(RoomRequest roomRequest) {
        return Room.builder()
                .title(roomRequest.getTitle())
                .image(roomRequest.getImage())
                .images(imageConverter.toSetOfImages(roomRequest.getImages()))
                .description(roomRequest.getDescription())
                .facilities(roomRequest.getFacilities())
                .area(roomRequest.getArea())
                .people(roomRequest.getPeople())
                .price(roomRequest.getPrice())
                .count(roomRequest.getCount())
                .build();
    }

    public RoomResponse toRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .images(imageConverter.toSetOfStrings(room.getImages()))
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .build();
    }
}

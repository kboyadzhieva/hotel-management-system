package com.moonlighthotel.hotelmanagementsystem.converter.roomreservation;

import com.moonlighthotel.hotelmanagementsystem.converter.user.UserConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestUpdate;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import com.moonlighthotel.hotelmanagementsystem.enumeration.BedType;
import com.moonlighthotel.hotelmanagementsystem.enumeration.StatusType;
import com.moonlighthotel.hotelmanagementsystem.enumeration.ViewType;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.Room;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.model.user.User;
import com.moonlighthotel.hotelmanagementsystem.service.RoomService;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoomReservationConverter {

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final DateFormatter dateFormatter;

    @Autowired
    private final RoomConverter roomConverter;

    @Autowired
    private final UserConverter userConverter;

    public RoomReservation toRoomReservation(Long id, RoomReservationRequestSave roomReservationRequestSave) {
        User foundUser = userService.findById(roomReservationRequestSave.getUser());
        Room foundRoom = roomService.findById(id);

        return RoomReservation.builder()
                .startDate(dateFormatter.stringToInstant(roomReservationRequestSave.getStartDate()))
                .endDate(dateFormatter.stringToInstant(roomReservationRequestSave.getEndDate()))
                .adults(roomReservationRequestSave.getAdults())
                .kids(roomReservationRequestSave.getKids())
                .typeBed(BedType.findByName(roomReservationRequestSave.getTypeBed()))
                .view(ViewType.findByName(roomReservationRequestSave.getView()))
                .room(foundRoom)
                .user(foundUser)
                .build();
    }

    public RoomReservation toRoomReservation(Long id, RoomReservationRequestUpdate roomReservationRequestUpdate) {
        User foundUser = userService.findById(roomReservationRequestUpdate.getUser());
        Room foundRoom = roomService.findById(id);

        return RoomReservation.builder()
                .startDate(dateFormatter.stringToInstant(roomReservationRequestUpdate.getStartDate()))
                .endDate(dateFormatter.stringToInstant(roomReservationRequestUpdate.getEndDate()))
                .adults(roomReservationRequestUpdate.getAdults())
                .kids(roomReservationRequestUpdate.getKids())
                .typeBed(BedType.findByName(roomReservationRequestUpdate.getTypeBed()))
                .view(ViewType.findByName(roomReservationRequestUpdate.getView()))
                .room(foundRoom)
                .user(foundUser)
                .status(StatusType.valueOf(roomReservationRequestUpdate.getStatus()))
                .build();
    }

    public RoomReservationSaveResponse toRoomReservationSaveResponse(RoomReservation roomReservation) {
        return RoomReservationSaveResponse.builder()
                .id(roomReservation.getId())
                .startDate(dateFormatter.instantToString(roomReservation.getStartDate()))
                .endDate(dateFormatter.instantToString(roomReservation.getEndDate()))
                .days(roomReservation.getDays())
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .price(roomReservation.getPrice())
                .room(roomConverter.toRoomResponse(roomReservation.getRoom()))
                .build();
    }

    public RoomReservationResponse toRoomReservationResponse(RoomReservation roomReservation) {
        return RoomReservationResponse.builder()
                .id(roomReservation.getId())
                .startDate(dateFormatter.instantToString(roomReservation.getStartDate()))
                .endDate(dateFormatter.instantToString(roomReservation.getEndDate()))
                .days(roomReservation.getDays())
                .adults(roomReservation.getAdults())
                .kids(roomReservation.getKids())
                .typeBed(roomReservation.getTypeBed().name().toLowerCase())
                .view(roomReservation.getView().name().toLowerCase())
                .price(roomReservation.getPrice())
                .created(dateFormatter.instantToString(roomReservation.getCreated()))
                .status(roomReservation.getStatus().name().toLowerCase())
                .room(roomConverter.toRoomResponse(roomReservation.getRoom()))
                .user(userConverter.toUserResponse(roomReservation.getUser()))
                .build();
    }
}

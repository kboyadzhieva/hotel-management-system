package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.RoomReservationConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import com.moonlighthotel.hotelmanagementsystem.model.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.service.RoomReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rooms/{id}/reservations")
@AllArgsConstructor
public class RoomReservationController {

    @Autowired
    private final RoomReservationService roomReservationService;

    @Autowired
    private final RoomReservationConverter roomReservationConverter;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<RoomReservationResponse>> findAll(@PathVariable Long id) {
        List<RoomReservation> roomReservations = roomReservationService.findAllByRoomId(id);
        List<RoomReservationResponse> roomReservationResponseList = roomReservations.stream()
                .map(roomReservationConverter::toRoomReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomReservationResponseList);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<RoomReservationSaveResponse> save(@PathVariable Long id,
                                                            @RequestBody RoomReservationRequestSave roomReservationRequestSave) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(id, roomReservationRequestSave);
        RoomReservation savedRoomReservation = roomReservationService.save(id, roomReservation);
        RoomReservationSaveResponse roomReservationSaveResponse = roomReservationConverter
                .toRoomReservationSaveResponse(savedRoomReservation);
        return ResponseEntity.ok(roomReservationSaveResponse);
    }
}

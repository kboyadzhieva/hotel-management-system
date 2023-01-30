package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.RoomReservationConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestUpdate;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import com.moonlighthotel.hotelmanagementsystem.model.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.service.RoomReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "rooms/{id}/reservations")
@AllArgsConstructor
public class RoomReservationController {

    @Autowired
    private final RoomReservationService roomReservationService;

    @Autowired
    private final RoomReservationConverter roomReservationConverter;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<RoomReservationSaveResponse> save(@PathVariable Long id,
                                                            @RequestBody RoomReservationRequestSave roomReservationRequestSave) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(id, roomReservationRequestSave);
        RoomReservation savedRoomReservation = roomReservationService.save(id, roomReservation);
        RoomReservationSaveResponse roomReservationSaveResponse = roomReservationConverter
                .toRoomReservationSaveResponse(savedRoomReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomReservationSaveResponse);
    }

    @DeleteMapping(value = "/{rid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id, @PathVariable Long rid) {
        roomReservationService.deleteById(id, rid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{rid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoomReservationResponse> update(@PathVariable Long id, @PathVariable Long rid,
                                                          @RequestBody RoomReservationRequestUpdate roomReservationUpdate) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(id, roomReservationUpdate);
        RoomReservation updatedRoomReservation = roomReservationService.update(id, rid, roomReservation);
        RoomReservationResponse roomReservationResponse = roomReservationConverter.toRoomReservationResponse(updatedRoomReservation);
        return ResponseEntity.ok(roomReservationResponse);
    }
}

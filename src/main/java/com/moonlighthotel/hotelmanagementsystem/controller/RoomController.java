package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.RoomConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.model.Room;
import com.moonlighthotel.hotelmanagementsystem.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rooms")
@AllArgsConstructor
public class RoomController {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final RoomConverter roomConverter;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll(RoomFilter roomFilter) {
        List<Room> rooms = roomService.findAll(roomFilter);

        List<RoomResponse> roomResponseList = rooms.stream()
                .map(roomConverter::toRoomResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomResponseList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        Room foundRoom = roomService.findById(id);
        RoomResponse roomResponse = roomConverter.toRoomResponse(foundRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> save(@RequestBody @Valid RoomRequest roomRequest) {
        Room room = roomConverter.toRoom(roomRequest);
        Room savedRoom = roomService.save(room);
        RoomResponse roomResponse = roomConverter.toRoomResponse(savedRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomResponse);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid RoomRequest roomRequest) {
        Room room = roomConverter.toRoom(roomRequest);
        Room updatedRoom = roomService.update(id, room);
        RoomResponse roomResponse = roomConverter.toRoomResponse(updatedRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

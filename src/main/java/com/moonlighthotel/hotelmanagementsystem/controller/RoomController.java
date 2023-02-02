package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.RoomConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.room.request.RoomRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.RecordNotFoundErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.ValidationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.filter.RoomFilter;
import com.moonlighthotel.hotelmanagementsystem.model.Room;
import com.moonlighthotel.hotelmanagementsystem.service.RoomService;
import com.moonlighthotel.hotelmanagementsystem.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = SwaggerConfiguration.ROOM_TAG)
public class RoomController {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final RoomConverter roomConverter;

    @GetMapping
    @Operation(summary = "Get available rooms")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = RoomFilter.class))})
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RoomResponse.class)))})
    public ResponseEntity<List<RoomResponse>> findAll(RoomFilter roomFilter) {
        List<Room> rooms = roomService.findAll(roomFilter);

        List<RoomResponse> roomResponseList = rooms.stream()
                .map(roomConverter::toRoomResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomResponseList);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Show a room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<RoomResponse> findById(@Parameter(description = "Room ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        Room foundRoom = roomService.findById(id);
        RoomResponse roomResponse = roomConverter.toRoomResponse(foundRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<RoomResponse> save(@RequestBody @Valid RoomRequest roomRequest) {
        Room room = roomConverter.toRoom(roomRequest);
        Room savedRoom = roomService.save(room);
        RoomResponse roomResponse = roomConverter.toRoomResponse(savedRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomResponse);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<RoomResponse> update(@Parameter(description = "Room ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                               @RequestBody @Valid RoomRequest roomRequest) {
        Room room = roomConverter.toRoom(roomRequest);
        Room updatedRoom = roomService.update(id, room);
        RoomResponse roomResponse = roomConverter.toRoomResponse(updatedRoom);
        return ResponseEntity.ok(roomResponse);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove a room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<HttpStatus> deleteById(@Parameter(description = "Room ID", content = @Content(
            schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

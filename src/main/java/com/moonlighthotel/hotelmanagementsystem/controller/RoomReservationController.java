package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.RoomReservationConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestSave;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request.RoomReservationRequestUpdate;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response.RoomReservationSaveResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.RecordNotFoundErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.ValidationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.service.RoomReservationService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rooms/{id}")
@AllArgsConstructor
@Tag(name = SwaggerConfiguration.ROOM_TAG)
public class RoomReservationController {

    @Autowired
    private final RoomReservationService roomReservationService;

    @Autowired
    private final RoomReservationConverter roomReservationConverter;

    @GetMapping(value = "/reservations")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List reservations by room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoomReservationResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<List<RoomReservationResponse>> findAll(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        List<RoomReservation> roomReservations = roomReservationService.findAllByRoomId(id);
        List<RoomReservationResponse> roomReservationResponseList = roomReservations.stream()
                .map(roomReservationConverter::toRoomReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomReservationResponseList);
    }

    @GetMapping(value = "/reservations/{rid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Show a Reservation by ID and room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationResponse.class))}),
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
    public ResponseEntity<RoomReservationResponse> findById(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                            @Parameter(description = "Reservation ID",
                                                                    content = @Content(schema = @Schema(
                                                                            type = "integer", format = "")))
                                                            @PathVariable Long rid) {
        RoomReservation roomReservation = roomReservationService.findById(id, rid);
        RoomReservationResponse roomReservationResponse = roomReservationConverter
                .toRoomReservationResponse(roomReservation);
        return ResponseEntity.ok(roomReservationResponse);
    }

    @PostMapping(value = "/reservations")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create user room reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationSaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<RoomReservationSaveResponse> save(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                            @RequestBody RoomReservationRequestSave roomReservationRequestSave) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(id, roomReservationRequestSave);
        RoomReservation savedRoomReservation = roomReservationService.save(id, roomReservation);
        RoomReservationSaveResponse roomReservationSaveResponse = roomReservationConverter
                .toRoomReservationSaveResponse(savedRoomReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomReservationSaveResponse);
    }

    @PutMapping(value = "/reservations/{rid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a Reservation by ID and room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationResponse.class))}),
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
    public ResponseEntity<RoomReservationResponse> update(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                          @Parameter(description = "Reservation ID",
                                                                  content = @Content(schema = @Schema(
                                                                          type = "integer", format = "")))
                                                          @PathVariable Long rid,
                                                          @RequestBody RoomReservationRequestUpdate roomReservationUpdate) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(id, roomReservationUpdate);
        RoomReservation updatedRoomReservation = roomReservationService.update(id, rid, roomReservation);
        RoomReservationResponse roomReservationResponse = roomReservationConverter.toRoomReservationResponse(updatedRoomReservation);
        return ResponseEntity.ok(roomReservationResponse);
    }

    @DeleteMapping(value = "/reservations/{rid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Remove a Reservation by ID and room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<HttpStatus> delete(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                             @Parameter(description = "Reservation ID",
                                                     content = @Content(schema = @Schema(
                                                             type = "integer", format = "")))
                                             @PathVariable Long rid) {
        roomReservationService.deleteById(id, rid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/summarize")
    @Operation(summary = "Calculate reservation info for room ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomReservationSaveResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<RoomReservationSaveResponse> summarize(@Parameter(description = "Room ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                                 @RequestBody RoomReservationRequestSave roomReservationRequestSave) {
        RoomReservation roomReservation = roomReservationConverter.toRoomReservation(id, roomReservationRequestSave);
        RoomReservation summarizedRoomReservation = roomReservationService.summarize(id, roomReservation);
        RoomReservationSaveResponse roomReservationResponse = roomReservationConverter
                .toRoomReservationSaveResponse(summarizedRoomReservation);
        return ResponseEntity.status(HttpStatus.OK).body(roomReservationResponse);
    }
}

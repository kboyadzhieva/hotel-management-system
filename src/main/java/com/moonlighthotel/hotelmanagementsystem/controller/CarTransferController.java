package com.moonlighthotel.hotelmanagementsystem.controller;

import com.moonlighthotel.hotelmanagementsystem.converter.transfer.CarTransferConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarTransferResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.RecordNotFoundErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.ValidationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import com.moonlighthotel.hotelmanagementsystem.service.CarTransferService;
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

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cars/{id}/transfers")
@Tag(name = SwaggerConfiguration.TRANSFER_TAG)
public class CarTransferController {

    @Autowired
    private final CarTransferConverter carTransferConverter;

    @Autowired
    private final CarTransferService carTransferService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get car transfers list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarTransferResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<List<CarTransferResponse>> findAll(@Parameter(description = "Car ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id) {
        List<CarTransfer> carTransfers = carTransferService.findAll(id);
        List<CarTransferResponse> carTransferResponseList = carTransfers.stream()
                .map(carTransferConverter::toCarTransferResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carTransferResponseList);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a car transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarTransferResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<CarTransferResponse> save(@Parameter(description = "Car ID",
            content = @Content(schema = @Schema(type = "integer", format = ""))) @PathVariable Long id,
                                                    @RequestBody @Valid CarTransferRequest carTransferRequest) {
        CarTransfer carTransfer = carTransferConverter.toCarTransfer(carTransferRequest);
        CarTransfer savedCarTransfer = carTransferService.save(id, carTransfer);
        CarTransferResponse carTransferResponse = carTransferConverter.toCarTransferResponse(savedCarTransfer);
        return ResponseEntity.status(HttpStatus.CREATED).body(carTransferResponse);
    }
}

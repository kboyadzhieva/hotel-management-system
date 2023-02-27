package com.moonlighthotel.hotelmanagementsystem.controller.restaurant;

import com.moonlighthotel.hotelmanagementsystem.converter.restaurant.RestaurantTableConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.restaurant.request.RestaurantTableRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.restaurant.response.RestaurantTableResponse;
import com.moonlighthotel.hotelmanagementsystem.exception.model.RecordNotFoundErrorModel;
import com.moonlighthotel.hotelmanagementsystem.exception.model.ValidationFailErrorModel;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import com.moonlighthotel.hotelmanagementsystem.service.RestaurantTableService;
import com.moonlighthotel.hotelmanagementsystem.swagger.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tables")
@Tag(name = SwaggerConfiguration.RESTAURANT_TAG)
public class RestaurantTableController {

    @Autowired
    private final RestaurantTableConverter restaurantTableConverter;

    @Autowired
    private final RestaurantTableService restaurantTableService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantTableResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationFailErrorModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordNotFoundErrorModel.class))})})
    public ResponseEntity<RestaurantTableResponse> save(@RequestBody @Valid RestaurantTableRequest tableRequest) {
        RestaurantTable table = restaurantTableConverter.toRestaurantTable(tableRequest);
        RestaurantTable savedTable = restaurantTableService.save(table);
        RestaurantTableResponse tableResponse = restaurantTableConverter.toRestaurantTableResponse(savedTable);
        return ResponseEntity.status(HttpStatus.CREATED).body(tableResponse);
    }
}

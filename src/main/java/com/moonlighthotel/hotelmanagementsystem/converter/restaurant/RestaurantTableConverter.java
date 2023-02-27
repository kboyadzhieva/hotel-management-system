package com.moonlighthotel.hotelmanagementsystem.converter.restaurant;

import com.moonlighthotel.hotelmanagementsystem.dto.restaurant.request.RestaurantTableRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.restaurant.response.RestaurantTableResponse;
import com.moonlighthotel.hotelmanagementsystem.enumeration.ZoneType;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableConverter {

    public RestaurantTable toRestaurantTable(RestaurantTableRequest restaurantTableRequest) {
        return RestaurantTable.builder()
                .zone(ZoneType.valueOf(restaurantTableRequest.getZone()))
                .number(restaurantTableRequest.getNumber())
                .people(restaurantTableRequest.getPeople())
                .build();
    }

    public RestaurantTableResponse toRestaurantTableResponse(RestaurantTable restaurantTable) {
        return RestaurantTableResponse.builder()
                .id(restaurantTable.getId())
                .zone(restaurantTable.getZone().name().toLowerCase())
                .number(restaurantTable.getNumber())
                .people(restaurantTable.getPeople())
                .smoking(restaurantTable.getSmoking().getValue())
                .build();
    }
}

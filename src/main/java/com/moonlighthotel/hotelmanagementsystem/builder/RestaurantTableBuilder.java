package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.enumeration.SectionType;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableBuilder {

    public RestaurantTable build(RestaurantTable restaurantTable, SectionType sectionType) {
        return RestaurantTable.builder()
                .id(restaurantTable.getId())
                .zone(restaurantTable.getZone())
                .number(restaurantTable.getNumber())
                .people(restaurantTable.getPeople())
                .smoking(sectionType)
                .build();
    }

    public RestaurantTable build(Long id, RestaurantTable restaurantTable, SectionType sectionType) {
        return RestaurantTable.builder()
                .id(id)
                .zone(restaurantTable.getZone())
                .number(restaurantTable.getNumber())
                .people(restaurantTable.getPeople())
                .smoking(sectionType)
                .build();
    }
}

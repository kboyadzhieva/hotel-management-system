package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;

import java.util.List;

public interface RestaurantTableService {

    List<RestaurantTable> findAll();

    RestaurantTable findById(Long id);

    RestaurantTable save(RestaurantTable restaurantTable);

    RestaurantTable update(Long id, RestaurantTable restaurantTable);

    void deleteById(Long id);
}

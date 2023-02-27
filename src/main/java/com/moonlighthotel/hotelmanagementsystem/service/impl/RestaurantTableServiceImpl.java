package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.RestaurantTableBuilder;
import com.moonlighthotel.hotelmanagementsystem.enumeration.SectionType;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import com.moonlighthotel.hotelmanagementsystem.repository.RestaurantTableRepository;
import com.moonlighthotel.hotelmanagementsystem.service.RestaurantTableService;
import com.moonlighthotel.hotelmanagementsystem.validator.RestaurantTableValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {

    @Autowired
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private final RestaurantTableValidator restaurantTableValidator;

    @Autowired
    private final RestaurantTableBuilder restaurantTableBuilder;

    @Override
    public List<RestaurantTable> findAll() {
        return restaurantTableRepository.findAll();
    }

    @Override
    public RestaurantTable findById(Long id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Table with id %d does not exist.", id)));
    }

    @Override
    public RestaurantTable save(RestaurantTable restaurantTable) {
        restaurantTableValidator.validateRestaurantTableForSave(restaurantTable);
        SectionType sectionType = restaurantTableValidator.getSectionType(restaurantTable);
        RestaurantTable builtRestaurantTable = restaurantTableBuilder.build(restaurantTable, sectionType);
        return restaurantTableRepository.save(builtRestaurantTable);
    }

    @Override
    public RestaurantTable update(Long id, RestaurantTable restaurantTable) {
        RestaurantTable foundRestaurantTable = findById(id);
        SectionType sectionType = restaurantTableValidator.getSectionType(restaurantTable);
        RestaurantTable builtRestaurantTable = restaurantTableBuilder
                .build(foundRestaurantTable.getId(), restaurantTable, sectionType);
        return restaurantTableRepository.save(builtRestaurantTable);
    }

    @Override
    public void deleteById(Long id) {
        RestaurantTable foundRestaurantTable = findById(id);
        restaurantTableRepository.deleteById(foundRestaurantTable.getId());
    }
}

package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.enumeration.SectionType;
import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import com.moonlighthotel.hotelmanagementsystem.repository.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RestaurantTableValidator {

    private static final String BAR = "bar";
    private static final String SALON = "salon";
    private static final String TERRACE = "terrace";

    private final RestaurantTableRepository restaurantTableRepository;

    public void validateRestaurantTableForSave(RestaurantTable restaurantTable) {
        validateTheTableNumber(restaurantTable);
    }

    public SectionType getSectionType(RestaurantTable restaurantTable) {
        SectionType sectionType = null;

        if (restaurantTable.getZone().name().equalsIgnoreCase(BAR) ||
                restaurantTable.getZone().name().equalsIgnoreCase(SALON)) {
            sectionType = SectionType.NONSMOKING;
        } else if (restaurantTable.getZone().name().equalsIgnoreCase(TERRACE)) {
            sectionType = SectionType.SMOKING;
        }
        return sectionType;
    }

    private void validateTheTableNumber(RestaurantTable restaurantTable) {
        Optional<RestaurantTable> foundTable = restaurantTableRepository.findByNumber(restaurantTable.getNumber());
        Integer number = restaurantTable.getNumber();

        if (foundTable.isPresent()) {
            throw new DuplicateRecordException(
                    String.format("Table №%s already exists.", number), "number");
        }
    }
}

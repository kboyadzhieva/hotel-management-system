package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.enumeration.SectionType;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableValidator {
    private static final String BAR = "bar";
    private static final String SALON = "salon";
    private static final String TERRACE = "terrace";

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
}

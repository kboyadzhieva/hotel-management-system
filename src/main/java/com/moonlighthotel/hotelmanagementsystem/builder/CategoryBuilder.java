package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import org.springframework.stereotype.Component;

@Component
public class CategoryBuilder {

    public CarCategory build(CarCategory carCategory) {
        return CarCategory.builder()
                .id(carCategory.getId())
                .title(carCategory.getTitle())
                .seats(carCategory.getSeats())
                .price(carCategory.getPrice())
                .build();
    }

    public CarCategory build(Long id, CarCategory carCategory) {
        return CarCategory.builder()
                .id(id)
                .title(carCategory.getTitle())
                .seats(carCategory.getSeats())
                .price(carCategory.getPrice())
                .build();
    }
}

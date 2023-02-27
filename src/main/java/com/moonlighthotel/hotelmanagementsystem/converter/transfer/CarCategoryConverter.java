package com.moonlighthotel.hotelmanagementsystem.converter.transfer;

import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarCategoryRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarCategoryResponse;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import org.springframework.stereotype.Component;

@Component
public class CarCategoryConverter {

    public CarCategory toCategory(CarCategoryRequest carCategoryRequest) {
        return CarCategory.builder()
                .title(carCategoryRequest.getTitle())
                .seats(carCategoryRequest.getSeats())
                .price(carCategoryRequest.getPrice())
                .build();
    }

    public CarCategoryResponse toCategoryResponse(CarCategory carCategory) {
        return CarCategoryResponse.builder()
                .id(carCategory.getId())
                .title(carCategory.getTitle())
                .seats(carCategory.getSeats())
                .price(carCategory.getPrice())
                .build();
    }
}

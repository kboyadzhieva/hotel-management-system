package com.moonlighthotel.hotelmanagementsystem.converter.transfer;

import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryResponse;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import org.springframework.stereotype.Component;

@Component
public class CarCategoryConverter {

    public CarCategory toCategory(CategoryRequest categoryRequest) {
        return CarCategory.builder()
                .title(categoryRequest.getTitle())
                .seats(categoryRequest.getSeats())
                .price(categoryRequest.getPrice())
                .build();
    }

    public CategoryResponse toCategoryResponse(CarCategory carCategory) {
        return CategoryResponse.builder()
                .id(carCategory.getId())
                .title(carCategory.getTitle())
                .seats(carCategory.getSeats())
                .price(carCategory.getPrice())
                .build();
    }
}

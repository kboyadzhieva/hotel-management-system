package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryResponse;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public Category toCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .title(categoryRequest.getTitle())
                .seats(categoryRequest.getSeats())
                .price(categoryRequest.getPrice())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .seats(category.getSeats())
                .price(category.getPrice())
                .build();
    }
}

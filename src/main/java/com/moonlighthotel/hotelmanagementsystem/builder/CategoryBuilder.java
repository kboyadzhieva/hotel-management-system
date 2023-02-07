package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryBuilder {

    public Category build(Category category) {
        return Category.builder()
                .id(category.getId())
                .title(category.getTitle())
                .seats(category.getSeats())
                .price(category.getPrice())
                .build();
    }

    public Category build(Long id, Category category) {
        return Category.builder()
                .id(id)
                .title(category.getTitle())
                .seats(category.getSeats())
                .price(category.getPrice())
                .build();
    }
}

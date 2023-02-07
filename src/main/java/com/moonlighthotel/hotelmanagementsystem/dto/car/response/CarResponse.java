package com.moonlighthotel.hotelmanagementsystem.dto.car.response;

import com.moonlighthotel.hotelmanagementsystem.dto.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CarResponse {

    private Long id;

    private String brand;

    private String model;

    private String image;

    private Set<String> images;

    private Integer year;

    private String created;

    private CategoryResponse category;
}

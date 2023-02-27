package com.moonlighthotel.hotelmanagementsystem.dto.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CarCategoryResponse {

    private Long id;

    private String title;

    private Integer seats;

    private Double price;
}

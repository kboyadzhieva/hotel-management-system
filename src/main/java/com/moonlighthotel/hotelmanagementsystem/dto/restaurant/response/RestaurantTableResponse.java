package com.moonlighthotel.hotelmanagementsystem.dto.restaurant.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RestaurantTableResponse {

    private Long id;

    private String zone;

    private Integer number;

    private Integer people;

    private Integer smoking;
}

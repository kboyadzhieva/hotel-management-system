package com.moonlighthotel.hotelmanagementsystem.dto.restaurant.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RestaurantTableRequest {

    @NotNull(message = "Please select zone.")
    private Integer zone;

    @NotNull(message = "Please enter the number of the table.")
    @Positive(message = "The value must be greater than 0.")
    private Integer number;

    @NotNull(message = "Please enter the total number of people.")
    @Positive(message = "The value must be greater than 0.")
    private Integer people;
}

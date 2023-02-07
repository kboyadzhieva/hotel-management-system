package com.moonlighthotel.hotelmanagementsystem.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CategoryRequest {

    @Length(min = 2, max = 255)
    @NotBlank(message = "A title is required. Please enter a title of the category.")
    private String title;

    @NotNull(message = "Please enter the number of seats.")
    @Positive(message = "The value must be greater than 0.")
    @Max(value = 8, message = "The number of the seats must be less or equal to 8.")
    private Integer seats;

    @NotNull(message = "Please enter a price per one day.")
    @Positive(message = "The value must be greater than 0.")
    private Double price;
}

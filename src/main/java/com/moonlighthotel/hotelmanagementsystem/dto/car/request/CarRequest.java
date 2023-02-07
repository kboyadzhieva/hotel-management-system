package com.moonlighthotel.hotelmanagementsystem.dto.car.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CarRequest {

    @NotNull(message = "A category is required.")
    private Long category;

    @Length(min = 2, max = 255)
    @NotBlank(message = "A brand is required. Please enter the brand of the car.")
    private String brand;

    @Length(min = 2, max = 255)
    @NotBlank(message = "A model is required. Please enter the model of the car.")
    private String model;

    @NotBlank(message = "An Image is required. Please add an image.")
    @URL(message = "Please enter a valid URL.")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)",
            message = "The image should be in one of the following formats: JPEG, JPG, PNG, GIF, BMP.")
    private String image;

    @NotEmpty(message = "Images are required. Please add images.")
    private Set<@URL(message = "Please enter a valid URL.")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)",
            message = "The image should be in one of the following formats: JPEG, JPG, PNG, GIF, BMP.")
            String> images;

    @NotNull
    private Integer year;
}

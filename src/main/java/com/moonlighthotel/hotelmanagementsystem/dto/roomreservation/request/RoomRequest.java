package com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RoomRequest {

    @Length(min = 2, max = 255)
    @NotBlank(message = "A title is required. Please enter a title of the room.")
    private String title;

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

    @NotBlank(message = "Please enter a description of the room.")
    @Length(min = 2, max = 255)
    private String description;

    @NotBlank(message = "Please enter the facilities of the room.")
    @Length(min = 2, max = 255)
    private String facilities;

    @NotNull(message = "Please enter the room area.")
    @Positive(message = "The value must be greater than 0.")
    private Double area;

    @NotNull(message = "Please enter the maximum number of guests.")
    @Positive(message = "The value must be greater than 0.")
    private Integer people;

    @NotNull(message = "Please enter a price per night.")
    @Positive(message = "The value must be greater than 0.")
    private Double price;

    @NotNull(message = "Please enter the total number of rooms of that type.")
    @Positive(message = "The value must be greater than 0.")
    private Integer count;
}

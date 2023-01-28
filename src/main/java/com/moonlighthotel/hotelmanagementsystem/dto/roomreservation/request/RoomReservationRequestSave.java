package com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationRequestSave {

    @NotNull(message = "The user is required.")
    private Long user;

    @NotBlank(message = "Please enter start date")
    private String startDate;

    @NotBlank(message = "Please enter end date")
    private String endDate;

    @NotNull(message = "Please enter a number of adults.")
    @Min(value = 1)
    @Max(value = 4)
    private Integer adults;

    @NotNull(message = "Please enter a number of kids.")
    @Min(value = 0)
    @Max(value = 4)
    private Integer kids;

    @NotBlank(message = "Bed type choice is required")
    private String typeBed;

    @NotBlank(message = "View type choice is required")
    private String view;
}

package com.moonlighthotel.hotelmanagementsystem.dto.transfer.request;

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
public class CarTransferRequest {

    @NotBlank(message = "Please enter date.")
    private String date;

    @NotNull(message = "Please enter a number of seats.")
    @Min(value = 1)
    @Max(value = 8)
    private Integer seats;

    @NotBlank(message = "Please enter model.")
    private String model;

    @NotNull(message = "The user is required.")
    private Long user;
}

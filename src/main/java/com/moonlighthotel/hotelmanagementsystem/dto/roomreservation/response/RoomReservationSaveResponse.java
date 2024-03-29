package com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationSaveResponse {

    private Long id;

    private String startDate;

    private String endDate;

    private Integer days;

    private Integer adults;

    private Integer kids;

    private Double price;

    private RoomResponse room;
}

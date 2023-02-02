package com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationResponse {

    private Long id;

    private String startDate;

    private String endDate;

    private Integer days;

    private Integer adults;

    private Integer kids;

    private String typeBed;

    private String view;

    private Double price;

    private String created;

    private String status;

    private RoomResponse room;

    private UserResponse user;
}

package com.moonlighthotel.hotelmanagementsystem.dto.roomreservation.response;

import com.moonlighthotel.hotelmanagementsystem.dto.room.response.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
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

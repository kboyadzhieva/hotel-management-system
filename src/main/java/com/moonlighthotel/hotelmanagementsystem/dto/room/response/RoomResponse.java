package com.moonlighthotel.hotelmanagementsystem.dto.room.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RoomResponse {

    private Long id;

    private String title;

    private String image;

    private Set<String> images;

    private String description;

    private String facilities;

    private Double area;

    private Integer people;

    private Double price;
}

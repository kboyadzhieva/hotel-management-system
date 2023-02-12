package com.moonlighthotel.hotelmanagementsystem.dto.transfer.response;

import com.moonlighthotel.hotelmanagementsystem.dto.car.response.CarResponse;
import com.moonlighthotel.hotelmanagementsystem.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CarTransferResponse {

    private Long id;

    private Double price;

    private String date;

    private String created;

    private CarResponse car;

    private UserResponse user;
}

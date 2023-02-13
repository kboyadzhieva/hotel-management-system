package com.moonlighthotel.hotelmanagementsystem.converter.transfer;

import com.moonlighthotel.hotelmanagementsystem.converter.user.UserConverter;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.request.CarTransferRequest;
import com.moonlighthotel.hotelmanagementsystem.dto.transfer.response.CarTransferResponse;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import com.moonlighthotel.hotelmanagementsystem.model.user.User;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class CarTransferConverter {

    @Autowired
    private final UserService userService;

    @Autowired
    private final DateFormatter dateFormatter;

    @Autowired
    private final CarConverter carConverter;

    @Autowired
    private final UserConverter userConverter;

    public CarTransfer toCarTransfer(CarTransferRequest carTransferRequest) {
        User foundUser = userService.findById(carTransferRequest.getUser());

        return CarTransfer.builder()
                .date(dateFormatter.stringToInstant(carTransferRequest.getDate()))
                .created(Instant.now())
                .seats(carTransferRequest.getSeats())
                .user(foundUser)
                .build();
    }

    public CarTransferResponse toCarTransferResponse(CarTransfer carTransfer) {
        return CarTransferResponse.builder()
                .id(carTransfer.getId())
                .price(carTransfer.getPrice())
                .date(dateFormatter.instantToString(carTransfer.getDate()))
                .created(dateFormatter.instantToString(carTransfer.getCreated()))
                .car(carConverter.toCarResponse(carTransfer.getCar()))
                .user(userConverter.toUserResponse(carTransfer.getUser()))
                .build();
    }
}

package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import org.springframework.stereotype.Component;

@Component
public class CarTransferBuilder {

    public CarTransfer build(Car car, CarTransfer carTransfer) {
        return CarTransfer.builder()
                .id(carTransfer.getId())
                .date(carTransfer.getDate())
                .seats(carTransfer.getSeats())
                .created(carTransfer.getCreated())
                .car(car)
                .price(car.getCarCategory().getPrice())
                .user(carTransfer.getUser())
                .build();
    }

    public CarTransfer build(Long tid, CarTransfer carTransfer) {
        return CarTransfer.builder()
                .id(tid)
                .date(carTransfer.getDate())
                .seats(carTransfer.getSeats())
                .created(carTransfer.getCreated())
                .car(carTransfer.getCar())
                .price(carTransfer.getPrice())
                .user(carTransfer.getUser())
                .build();
    }
}

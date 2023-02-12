package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CarBuilder {

    public Car build(Car car) {
        return Car.builder()
                .id(car.getId())
                .carCategory(car.getCarCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(car.getImages())
                .year(car.getYear())
                .created(Instant.now())
                .build();
    }

    public Car build(Long id, Car car) {
        return Car.builder()
                .id(id)
                .carCategory(car.getCarCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(car.getImages())
                .year(car.getYear())
                .created(Instant.now())
                .build();
    }
}

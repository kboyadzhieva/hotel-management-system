package com.moonlighthotel.hotelmanagementsystem.builder;

import com.moonlighthotel.hotelmanagementsystem.model.car.Car;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CarBuilder {

    public Car build(Car car) {
        return Car.builder()
                .id(car.getId())
                .category(car.getCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .carImages(car.getCarImages())
                .year(car.getYear())
                .created(Instant.now())
                .build();
    }

    public Car build(Long id, Car car) {
        return Car.builder()
                .id(id)
                .category(car.getCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .carImages(car.getCarImages())
                .year(car.getYear())
                .created(Instant.now())
                .build();
    }
}

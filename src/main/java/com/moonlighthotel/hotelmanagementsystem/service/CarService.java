package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;

import java.util.List;

public interface CarService {

    List<Car> findAll();

    Car findById(Long id);

    Car save(Car car);

    Car update(Long id, Car car);

    void deleteById(Long id);
}

package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CarBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.repository.CarRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CarService;
import com.moonlighthotel.hotelmanagementsystem.validator.CarValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final CarValidator carValidator;

    @Autowired
    private final CarBuilder carBuilder;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("A car with id %d does not exist.", id)));
    }

    @Override
    public Car save(Car car) {
        carValidator.validateCar(car);
        Car builtCar = carBuilder.build(car);
        return carRepository.save(builtCar);
    }

    @Override
    public Car update(Long id, Car car) {
        carValidator.validateCarForUpdate(id, car);
        Car builtCar = carBuilder.build(id, car);
        return carRepository.save(builtCar);
    }

    @Override
    public void deleteById(Long id) {
        Car foundCar = findById(id);
        carRepository.deleteById(foundCar.getId());
    }
}

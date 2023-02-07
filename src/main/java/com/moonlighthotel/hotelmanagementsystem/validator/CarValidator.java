package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.car.Car;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
import com.moonlighthotel.hotelmanagementsystem.repository.CarRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CarValidator {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final CarRepository carRepository;

    public void validateCar(Car car) {
        validateCategoryExists(car.getCategory());
        validateModel(car.getModel());
    }

    public void validateCarForUpdate(Long id, Car car) {
        validateCarExists(id);
        validateCategoryExists(car.getCategory());
        validateModel(id, car.getModel());
    }

    private void validateCategoryExists(Category category) {
        categoryService.findById(category.getId());
    }

    private void validateModel(String model) {
        Optional<Car> foundCar = carRepository.findByModel(model);

        if (foundCar.isPresent()) {
            throw new DuplicateRecordException(
                    String.format("Car model '%s' already exists.", model), "model");
        }
    }

    private void validateModel(Long id, String model) {
        Optional<Car> foundCar = carRepository.findByModel(model);

        if (foundCar.isPresent() && !foundCar.get().getId().equals(id)) {
            throw new DuplicateRecordException(
                    String.format("Car model '%s' already exists.", model), "model");
        }
    }

    private void validateCarExists(Long id) {
        carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("A Car with id '%s' does not exist.", id)));
    }
}

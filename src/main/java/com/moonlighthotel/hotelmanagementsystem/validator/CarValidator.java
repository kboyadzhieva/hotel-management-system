package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import com.moonlighthotel.hotelmanagementsystem.repository.CarRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
@Component
public class CarValidator {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final DateFormatter dateFormatter;

    public void validateCar(Car car) {
        validateCategoryExists(car.getCarCategory());
        validateModel(car.getModel());
    }

    public void validateCarForUpdate(Long id, Car car) {
        validateCarExists(id);
        validateCategoryExists(car.getCarCategory());
        validateModel(id, car.getModel());
    }

    public void validateCarExists(Long id) {
        carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("A car with id '%s' does not exist.", id)));
    }

    public void validateIfTheCarIsAvailable(Car car, CarTransfer carTransfer) {
        String date = dateFormatter.formatDate(carTransfer.getDate());
        boolean isTheCarAvailable = isTheCarAvailable(car.getId(), carTransfer.getDate());

        if (!isTheCarAvailable) {
            throw new InvalidRequestException(String
                    .format("'%s' is not available for %s. Please enter a different date or choose another car.",
                            car.getModel(), date), "date");
        }
    }

    private void validateCategoryExists(CarCategory carCategory) {
        categoryService.findById(carCategory.getId());
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

    private boolean isTheCarAvailable(Long id, Instant date) {
        return carRepository.isTheCarAvailable(id, date);
    }
}

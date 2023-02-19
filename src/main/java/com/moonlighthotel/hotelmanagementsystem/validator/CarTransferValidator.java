package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CarTransferValidator {

    @Autowired
    private final DateValidator dateValidator;

    @Autowired
    private final CarValidator carValidator;

    public void validateCarTransfer(Car car, CarTransfer carTransfer) {
        dateValidator.validateDate(carTransfer.getDate());
        carValidator.validateIfTheCarIsAvailable(car, carTransfer);
        validateNumberOfSeats(car, carTransfer);
    }

    private void validateNumberOfSeats(Car car, CarTransfer carTransfer) {
        Integer categorySeats = car.getCarCategory().getSeats();
        if (carTransfer.getSeats() > categorySeats) {
            throw new InvalidRequestException(
                    String.format("In the '%s' category each car has only %d seats. " +
                                    "Please enter a number less than or equal to %d or choose a car from another category.",
                            car.getCarCategory().getTitle(), categorySeats, categorySeats), "seats");
        }
    }
}

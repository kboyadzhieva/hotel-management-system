package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CarTransferValidatorTest {

    @Mock
    private DateValidator dateValidator;

    @Mock
    private CarValidator carValidator;

    @InjectMocks
    private CarTransferValidator carTransferValidator;

    @Test
    public void verifyValidateCarTransfer() {
        String title = "Sports car";
        Integer seats = 3;
        CarCategory carCategory = CarCategory.builder().title(title).seats(seats).build();
        Car car = Car.builder().carCategory(carCategory).build();
        Instant date = Instant.now().plus(2, ChronoUnit.DAYS);
        CarTransfer carTransfer = CarTransfer.builder().date(date).seats(seats).build();

        carTransferValidator.validateCarTransfer(car, carTransfer);

        verify(dateValidator, times(1)).validateDate(carTransfer.getDate());
        verify(carValidator, times(1)).validateIfTheCarIsAvailable(car, carTransfer);
    }

    @Test
    public void validateThatMoreSeatsThanTheCarCategoryHasThrowsInvalidRequestException() {
        String title = "Sports car";
        Integer categorySeats = 3;
        CarCategory carCategory = CarCategory.builder().title(title).seats(categorySeats).build();
        Car car = Car.builder().carCategory(carCategory).build();
        CarTransfer carTransfer = CarTransfer.builder().car(car).seats(5).build();

        Throwable thrown = catchThrowable(() -> carTransferValidator.validateCarTransfer(car, carTransfer));

        assertThat(thrown)
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage(String
                        .format("In the '%s' category each car has only %d seats. " +
                                        "Please enter a number less than %d or choose a car from another category.",
                                car.getCarCategory().getTitle(), categorySeats, categorySeats), "seats");
    }
}

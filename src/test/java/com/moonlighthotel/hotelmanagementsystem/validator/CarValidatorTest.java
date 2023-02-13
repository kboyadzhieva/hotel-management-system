package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.formatter.DateFormatter;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import com.moonlighthotel.hotelmanagementsystem.repository.CarRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarValidatorTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private DateFormatter dateFormatter;

    @InjectMocks
    private CarValidator carValidator;

    @Test
    public void verifyValidateCar() {
        Long id = 1L;
        String model = "model";
        CarCategory carCategory = CarCategory.builder().id(id).build();
        Car car = Car.builder().carCategory(carCategory).model(model).build();

        when(categoryService.findById(any(Long.class))).thenReturn(carCategory);
        when(carRepository.findByModel(any())).thenReturn(Optional.empty());

        carValidator.validateCar(car);

        verify(categoryService, times(1)).findById(id);
        verify(carRepository, times(1)).findByModel(model);
    }

    @Test
    public void validateThatADuplicateModelNameThrowsDuplicateRecordException() {
        String model = "model";
        CarCategory carCategory = CarCategory.builder().build();
        Car car = Car.builder().carCategory(carCategory).model(model).build();

        when(carRepository.findByModel(any())).thenReturn(Optional.of(car));

        Throwable thrown = catchThrowable(() -> carValidator.validateCar(car));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("Car model '%s' already exists.", model), "model");
    }

    @Test
    public void verifyValidateCarForUpdate() {
        Long id = 1L;
        String model = "model";
        CarCategory carCategory = CarCategory.builder().id(id).build();
        Car car = Car.builder().id(id).carCategory(carCategory).model(model).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        when(categoryService.findById(any(Long.class))).thenReturn(carCategory);
        when(carRepository.findByModel(any())).thenReturn(Optional.of(car));

        carValidator.validateCarForUpdate(id, car);

        verify(carRepository, times(1)).findById(id);
        verify(categoryService, times(1)).findById(id);
        verify(carRepository, times(1)).findByModel(model);
    }

    @Test
    public void validateThatADuplicateModelNameWhenUpdatingACarThrowsDuplicateRecordException() {
        Long id = 12L;
        String model = "model";
        CarCategory carCategory = CarCategory.builder().build();
        Car car = Car.builder().id(1L).carCategory(carCategory).model(model).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        when(carRepository.findByModel(any())).thenReturn(Optional.of(car));

        Throwable thrown = catchThrowable(() -> carValidator.validateCarForUpdate(id, car));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("Car model '%s' already exists.", model), "model");
    }

    @Test
    public void verifyValidateIfTheCarIsAvailable() {
        Car car = Car.builder().id(1L).build();
        Instant date = Instant.now().plus(2, ChronoUnit.DAYS);
        CarTransfer carTransfer = CarTransfer.builder().date(date).build();

        when(carRepository.isTheCarAvailable(any(Long.class), any())).thenReturn(true);
        carValidator.validateIfTheCarIsAvailable(car, carTransfer);

        verify(dateFormatter, times(1)).formatDate(carTransfer.getDate());
        verify(carRepository, times(1)).isTheCarAvailable(car.getId(), date);
    }

    @Test
    public void validateThatWhenCarIsUnavailableThrowsInvalidRequestException() {
        String model = "model";
        Car car = Car.builder().id(1L).model(model).build();
        Instant date = Instant.now().plus(2, ChronoUnit.DAYS);
        CarTransfer carTransfer = CarTransfer.builder().date(date).build();

        when(carRepository.isTheCarAvailable(any(Long.class), any())).thenReturn(false);

        Throwable thrown = catchThrowable(() -> carValidator.validateIfTheCarIsAvailable(car, carTransfer));

        assertThat(thrown)
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage(String
                        .format("'%s' is not available for %s. Please enter a different date or choose another car.",
                                car.getModel(), dateFormatter.formatDate(carTransfer.getDate())), "date");
    }
}

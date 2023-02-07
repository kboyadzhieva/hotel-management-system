package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.model.car.Car;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
import com.moonlighthotel.hotelmanagementsystem.repository.CarRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    @InjectMocks
    private CarValidator carValidator;

    @Test
    public void verifyValidateCar() {
        Long id = 1L;
        String model = "model";
        Category category = Category.builder().id(id).build();
        Car car = Car.builder().category(category).model(model).build();

        when(categoryService.findById(any(Long.class))).thenReturn(category);
        when(carRepository.findByModel(any())).thenReturn(Optional.empty());

        carValidator.validateCar(car);

        verify(categoryService, times(1)).findById(id);
        verify(carRepository, times(1)).findByModel(model);
    }

    @Test
    public void validateThatADuplicateModelNameThrowsDuplicateRecordException() {
        String model = "model";
        Category category = Category.builder().build();
        Car car = Car.builder().category(category).model(model).build();

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
        Category category = Category.builder().id(id).build();
        Car car = Car.builder().id(id).category(category).model(model).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        when(categoryService.findById(any(Long.class))).thenReturn(category);
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
        Category category = Category.builder().build();
        Car car = Car.builder().id(1L).category(category).model(model).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        when(carRepository.findByModel(any())).thenReturn(Optional.of(car));

        Throwable thrown = catchThrowable(() -> carValidator.validateCarForUpdate(id, car));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("Car model '%s' already exists.", model), "model");
    }
}

package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CarBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.repository.CarRepository;
import com.moonlighthotel.hotelmanagementsystem.validator.CarValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarValidator carValidator;

    @Mock
    private CarBuilder carBuilder;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void verifyFindAll() {
        when(carRepository.findAll()).thenReturn(List.of(Car.builder().build()));
        carService.findAll();

        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void validateThatFindAllReturnsAList() {
        when(carRepository.findAll()).thenReturn(List.of(Car.builder().build()));
        assertThat(carService.findAll()).isInstanceOf(List.class);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;

        when(carRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(Car.builder().build()));
        carService.findById(id);

        verify(carRepository, times(1)).findById(id);
    }

    @Test
    public void validateThatFindByNonExistentIdReturnsRecordNotFoundException() {
        Long id = 1L;

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> carService.findById(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("A car with id %d does not exist.", id));
    }

    @Test
    public void validateThatFindByIdReturnsCar() {
        Long id = 1L;
        Car car = Car.builder().id(id).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        assertThat(carService.findById(car.getId())).isInstanceOf(Car.class);
    }

    @Test
    public void validateThatFindByIdReturnsCarAndItIsNotNull() {
        Long id = 1L;
        Car car = Car.builder().id(id).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        assertThat(carService.findById(car.getId())).isNotNull();
    }

    @Test
    public void verifySave() {
        Car car = Car.builder().id(1L).build();

        when(carBuilder.build(any())).thenReturn(car);

        carService.save(car);

        verify(carValidator, times(1)).validateCar(car);
        verify(carBuilder, times(1)).build(car);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void verifyUpdate() {
        Long id = 1L;
        Car car = Car.builder().id(1L).build();

        when(carBuilder.build(any(Long.class), any())).thenReturn(car);

        carService.update(id, car);

        verify(carValidator, times(1)).validateCarForUpdate(id, car);
        verify(carBuilder, times(1)).build(id, car);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        Car car = Car.builder().id(1L).build();

        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));

        carService.deleteById(id);

        verify(carRepository, times(1)).findById(id);
        verify(carRepository, times(1)).deleteById(id);
    }
}

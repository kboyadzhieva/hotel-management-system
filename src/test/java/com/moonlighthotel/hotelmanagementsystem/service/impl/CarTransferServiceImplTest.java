package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CarTransferBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import com.moonlighthotel.hotelmanagementsystem.repository.CarTransferRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CarService;
import com.moonlighthotel.hotelmanagementsystem.validator.CarTransferValidator;
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
public class CarTransferServiceImplTest {

    @Mock
    private CarValidator carValidator;

    @Mock
    private CarTransferRepository carTransferRepository;

    @Mock
    private CarService carService;

    @Mock
    private CarTransferValidator carTransferValidator;

    @Mock
    private CarTransferBuilder carTransferBuilder;

    @InjectMocks
    private CarTransferServiceImpl carTransferService;

    @Test
    public void verifyFindAll() {
        Long id = 1L;

        when(carTransferRepository.findAllByCarId(any(Long.class)))
                .thenReturn(List.of(CarTransfer.builder().build()));
        carTransferService.findAll(id);

        verify(carValidator, times(1)).validateCarExists(id);
        verify(carTransferRepository, times(1)).findAllByCarId(id);
    }

    @Test
    public void validateThatFindAllReturnsAList() {
        Long id = 1L;

        when(carTransferRepository.findAllByCarId(any(Long.class)))
                .thenReturn(List.of(CarTransfer.builder().build()));

        assertThat(carTransferService.findAll(id)).isInstanceOf(List.class);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;
        Long tid = 1L;

        when(carTransferRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(CarTransfer.builder().build()));

        carTransferService.findById(id, tid);

        verify(carValidator, times(1)).validateCarExists(id);
        verify(carTransferRepository, times(1)).findById(tid);
    }

    @Test
    public void validateThatFindByNonExistentIdThrowsRecordNotFoundException() {
        Long id = 1L;
        Long tid = 1L;

        when(carTransferRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> carTransferService.findById(id, tid));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("Car transfer with id %d does not exist.", tid));
    }

    @Test
    public void validateThatFindByIdReturnsCarTransfer() {
        Long id = 1L;
        Long tid = 1L;

        CarTransfer carTransfer = CarTransfer.builder().id(tid).build();

        when(carTransferRepository.findById(any(Long.class))).thenReturn(Optional.of(carTransfer));
        assertThat(carTransferService.findById(id, tid)).isInstanceOf(CarTransfer.class);
    }

    @Test
    public void verifySave() {
        Long id = 1L;
        Long tid = 1L;
        Car car = Car.builder().id(id).build();
        CarTransfer carTransfer = CarTransfer.builder().id(tid).build();

        when(carService.findById(any(Long.class))).thenReturn((car));
        when(carTransferBuilder.build(any(Car.class), any(CarTransfer.class))).thenReturn(carTransfer);

        carTransferService.save(id, carTransfer);

        verify(carService, times(1)).findById(id);
        verify(carTransferValidator, times(1)).validateCarTransfer(car, carTransfer);
        verify(carTransferBuilder, times(1)).build(car, carTransfer);
        verify(carTransferRepository, times(1)).save(carTransfer);
    }

    @Test
    public void verifyUpdate() {
        Long id = 1L;
        Long tid = 1L;
        Car car = Car.builder().id(id).build();
        CarTransfer carTransfer = CarTransfer.builder().id(tid).car(car).build();

        when(carTransferRepository.findById(any(Long.class))).thenReturn(Optional.of(carTransfer));
        when(carTransferBuilder.build(any(Long.class), any(CarTransfer.class))).thenReturn(carTransfer);

        carTransferService.update(id, tid, carTransfer);

        verify(carTransferRepository, times(1)).findById(tid);
        verify(carTransferValidator, times(1))
                .validateCarTransfer(carTransfer.getCar(), carTransfer);
        verify(carTransferBuilder, times(1)).build(carTransfer.getCar().getId(), carTransfer);
        verify(carTransferRepository, times(1)).save(carTransfer);
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        Long tid = 1L;
        CarTransfer carTransfer = CarTransfer.builder().id(tid).build();

        when(carTransferRepository.findById(any(Long.class))).thenReturn(Optional.of(carTransfer));

        carTransferService.deleteById(id, tid);

        verify(carTransferRepository, times(1)).findById(id);
        verify(carTransferRepository, times(1)).deleteById(id);
    }
}

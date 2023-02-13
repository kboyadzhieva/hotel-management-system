package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CarTransferBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import com.moonlighthotel.hotelmanagementsystem.repository.CarTransferRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CarService;
import com.moonlighthotel.hotelmanagementsystem.service.CarTransferService;
import com.moonlighthotel.hotelmanagementsystem.validator.CarValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CarTransferServiceImpl implements CarTransferService {

    @Autowired
    private final CarValidator carValidator;

    @Autowired
    private final CarTransferRepository carTransferRepository;

    @Autowired
    private final CarService carService;

    @Autowired
    private final CarTransferBuilder carTransferBuilder;

    @Override
    public List<CarTransfer> findAll(Long id) {
        carValidator.validateCarExists(id);
        return carTransferRepository.findAllByCarId(id);
    }

    @Override
    public CarTransfer findById(Long id, Long tid) {
        carValidator.validateCarExists(id);
        return carTransferRepository.findById(tid)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Car transfer with id %d does not exist.", tid)));
    }

    @Override
    public CarTransfer save(Long id, CarTransfer carTransfer) {
        Car foundCar = carService.findById(id);
        CarTransfer builtCarTransfer = carTransferBuilder.build(foundCar, carTransfer);
        return carTransferRepository.save(builtCarTransfer);
    }

    @Override
    public CarTransfer update(Long id, Long tid, CarTransfer carTransfer) {
        CarTransfer foundCarTransfer = findById(id, tid);
        CarTransfer builtCarTransfer = carTransferBuilder.build(foundCarTransfer.getId(), carTransfer);
        return carTransferRepository.save(builtCarTransfer);
    }

    @Override
    public void deleteById(Long id, Long tid) {
        CarTransfer foundCarTransfer = findById(id, tid);
        carTransferRepository.deleteById(foundCarTransfer.getId());
    }
}

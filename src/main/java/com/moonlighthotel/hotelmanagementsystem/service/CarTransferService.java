package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;

import java.util.List;

public interface CarTransferService {

    List<CarTransfer> findAll(Long id);

    CarTransfer findById(Long id, Long tid);

    CarTransfer save(Long id, CarTransfer carTransfer);

    CarTransfer update(Long id, Long tid, CarTransfer carTransfer);

    void deleteById(Long id, Long tid);
}

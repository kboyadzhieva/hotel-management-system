package com.moonlighthotel.hotelmanagementsystem.repository;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarTransferRepository extends JpaRepository<CarTransfer, Long> {

    List<CarTransfer> findAllByCarId(Long id);
}

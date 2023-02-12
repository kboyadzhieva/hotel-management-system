package com.moonlighthotel.hotelmanagementsystem.repository;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByModel(String model);
}

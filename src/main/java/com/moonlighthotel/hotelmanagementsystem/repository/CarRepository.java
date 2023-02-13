package com.moonlighthotel.hotelmanagementsystem.repository;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByModel(String model);

    @Query("SELECT CASE " +
            "WHEN(COUNT(car_id) > 0) " +
            "THEN FALSE ELSE TRUE " +
            "END " +
            "FROM CarTransfer WHERE car_id = ?1 AND date = ?2")
    boolean isTheCarAvailable(Long id, Instant date);
}

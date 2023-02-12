package com.moonlighthotel.hotelmanagementsystem.repository;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CarCategory, Long> {

    Optional<CarCategory> findByTitle(String title);
}

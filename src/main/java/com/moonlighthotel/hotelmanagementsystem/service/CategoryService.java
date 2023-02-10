package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;

import java.util.List;

public interface CategoryService {

    List<CarCategory> findAll();

    CarCategory findById(Long id);

    CarCategory save(CarCategory carCategory);

    CarCategory update(Long id, CarCategory carCategory);

    void deleteById(Long id);
}

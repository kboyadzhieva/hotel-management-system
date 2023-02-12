package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CategoryBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.repository.CategoryRepository;
import com.moonlighthotel.hotelmanagementsystem.service.CategoryService;
import com.moonlighthotel.hotelmanagementsystem.validator.CategoryValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CategoryValidator categoryValidator;

    @Autowired
    private final CategoryBuilder categoryBuilder;

    @Override
    public List<CarCategory> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CarCategory findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Category with id %d does not exist", id)));
    }

    @Override
    public CarCategory save(CarCategory carCategory) {
        categoryValidator.validateCategory(carCategory);
        CarCategory builtCarCategory = categoryBuilder.build(carCategory);
        return categoryRepository.save(builtCarCategory);
    }

    @Override
    public CarCategory update(Long id, CarCategory carCategory) {
        categoryValidator.validateCategoryForUpdate(id, carCategory);
        CarCategory builtCarCategory = categoryBuilder.build(id, carCategory);
        return categoryRepository.save(builtCarCategory);
    }

    @Override
    public void deleteById(Long id) {
        CarCategory foundCarCategory = findById(id);
        categoryRepository.deleteById(foundCarCategory.getId());
    }
}

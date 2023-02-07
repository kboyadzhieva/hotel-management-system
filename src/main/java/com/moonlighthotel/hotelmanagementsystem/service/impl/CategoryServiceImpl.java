package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CategoryBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
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
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String
                        .format("Category with id %d does not exist", id)));
    }

    @Override
    public Category save(Category category) {
        categoryValidator.validateCategory(category);
        Category builtCategory = categoryBuilder.build(category);
        return categoryRepository.save(builtCategory);
    }

    @Override
    public Category update(Long id, Category category) {
        categoryValidator.validateCategoryForUpdate(id, category);
        Category builtCategory = categoryBuilder.build(id, category);
        return categoryRepository.save(builtCategory);
    }

    @Override
    public void deleteById(Long id) {
        Category foundCategory = findById(id);
        categoryRepository.deleteById(foundCategory.getId());
    }
}

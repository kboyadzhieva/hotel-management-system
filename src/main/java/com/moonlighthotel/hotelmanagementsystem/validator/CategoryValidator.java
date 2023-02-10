package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CategoryValidator {

    @Autowired
    private final CategoryRepository categoryRepository;

    public void validateCategory(CarCategory carCategory) {
        validateTitle(carCategory.getTitle());
    }

    public void validateCategoryForUpdate(Long id, CarCategory carCategory) {
        validateTitle(id, carCategory.getTitle());
    }

    private void validateTitle(String title) {
        Optional<CarCategory> foundCategory = categoryRepository.findByTitle(title);

        if (foundCategory.isPresent()) {
            throw new DuplicateRecordException(
                    String.format("Category with title '%s' already exists.", title), "title");
        }
    }

    private void validateTitle(Long id, String title) {
        Optional<CarCategory> foundCategory = categoryRepository.findByTitle(title);

        if (foundCategory.isPresent() && !foundCategory.get().getId().equals(id)) {
            throw new DuplicateRecordException(
                    String.format("Category with title '%s' already exists.", title), "title");
        }
    }
}

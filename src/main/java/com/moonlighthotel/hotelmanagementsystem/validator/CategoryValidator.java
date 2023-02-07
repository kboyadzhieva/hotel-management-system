package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
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

    public void validateCategory(Category category) {
        validateTitle(category.getTitle());
    }

    public void validateCategoryForUpdate(Long id, Category category) {
        validateTitle(id, category.getTitle());
    }

    private void validateTitle(String title) {
        Optional<Category> foundCategory = categoryRepository.findByTitle(title);

        if (foundCategory.isPresent()) {
            throw new DuplicateRecordException(
                    String.format("Category with title '%s' already exists.", title), "title");
        }
    }

    private void validateTitle(Long id, String title) {
        Optional<Category> foundCategory = categoryRepository.findByTitle(title);

        if (foundCategory.isPresent() && !foundCategory.get().getId().equals(id)) {
            throw new DuplicateRecordException(
                    String.format("Category with title '%s' already exists.", title), "title");
        }
    }
}

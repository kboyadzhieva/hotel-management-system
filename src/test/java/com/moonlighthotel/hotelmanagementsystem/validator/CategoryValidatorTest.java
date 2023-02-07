package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.model.category.Category;
import com.moonlighthotel.hotelmanagementsystem.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryValidatorTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryValidator categoryValidator;

    private Throwable thrown;

    @Test
    public void validateThatADuplicateTitleThrowsDuplicateRecordException() {
        String title = "Sports car";
        Category category = Category.builder().title(title).build();

        when(categoryRepository.findByTitle(any())).thenReturn(Optional.of(category));

        thrown = catchThrowable(() -> categoryValidator.validateCategory(category));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("Category with title '%s' already exists.", title), "title");
    }

    @Test
    public void validateThatADuplicateTitleWhenUpdatingACategoryThrowsDuplicateRecordException() {
        Long id = 10L;
        String title = "Sports car";
        Category category = Category.builder().id(1L).title(title).build();

        when(categoryRepository.findByTitle(any())).thenReturn(Optional.of(category));

        thrown = catchThrowable(() -> categoryValidator.validateCategoryForUpdate(id, category));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("Category with title '%s' already exists.", title), "title");
    }
}

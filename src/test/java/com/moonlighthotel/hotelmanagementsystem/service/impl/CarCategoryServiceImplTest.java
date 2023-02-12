package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.CategoryBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.transfer.CarCategory;
import com.moonlighthotel.hotelmanagementsystem.repository.CategoryRepository;
import com.moonlighthotel.hotelmanagementsystem.validator.CategoryValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarCategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryValidator categoryValidator;

    @Mock
    private CategoryBuilder categoryBuilder;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void verifyFindAll() {
        when(categoryRepository.findAll()).thenReturn(List.of(CarCategory.builder().build()));
        categoryService.findAll();

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void validateThatFindAllReturnsAList() {
        when(categoryRepository.findAll()).thenReturn(List.of(CarCategory.builder().build()));

        assertThat(categoryService.findAll()).isInstanceOf(List.class);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;

        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(CarCategory.builder().build()));
        categoryService.findById(id);

        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void validateThatFindByNonExistentIdReturnsRecordNotFoundException() {
        Long id = 1L;

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> categoryService.findById(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("Category with id %d does not exist", id));
    }

    @Test
    public void validateThatFindByIdReturnsCategory() {
        Long id = 1L;
        CarCategory carCategory = CarCategory.builder().id(id).build();

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(carCategory));
        assertThat(categoryService.findById(carCategory.getId())).isInstanceOf(CarCategory.class);
    }

    @Test
    public void validateThatFindByIdReturnsCategoryAndItIsNotNull() {
        Long id = 1L;
        CarCategory carCategory = CarCategory.builder().id(id).build();

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(carCategory));
        assertThat(categoryService.findById(carCategory.getId())).isNotNull();
    }

    @Test
    public void verifySave() {
        CarCategory carCategory = CarCategory.builder().id(1L).build();

        when(categoryBuilder.build(any())).thenReturn(carCategory);

        categoryService.save(carCategory);

        verify(categoryValidator, times(1)).validateCategory(carCategory);
        verify(categoryBuilder, times(1)).build(carCategory);
        verify(categoryRepository, times(1)).save(carCategory);
    }

    @Test
    public void verifyUpdate() {
        Long id = 1L;
        CarCategory carCategory = CarCategory.builder().id(1L).build();

        when(categoryBuilder.build(any(Long.class), any())).thenReturn(carCategory);

        categoryService.update(id, carCategory);

        verify(categoryValidator, times(1)).validateCategoryForUpdate(id, carCategory);
        verify(categoryBuilder, times(1)).build(id, carCategory);
        verify(categoryRepository, times(1)).save(carCategory);
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        CarCategory carCategory = CarCategory.builder().id(1L).build();

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(carCategory));

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }
}

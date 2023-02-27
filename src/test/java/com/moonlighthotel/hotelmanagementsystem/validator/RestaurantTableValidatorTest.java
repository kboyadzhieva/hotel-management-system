package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import com.moonlighthotel.hotelmanagementsystem.repository.RestaurantTableRepository;
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
public class RestaurantTableValidatorTest {

    @Mock
    private RestaurantTableRepository restaurantTableRepository;

    @InjectMocks
    private RestaurantTableValidator restaurantTableValidator;

    @Test
    public void validateThatDuplicateTableNumberThrowsDuplicateRecordException() {
        Long id = 1L;
        Integer number = 1;
        RestaurantTable restaurantTable = RestaurantTable.builder().id(id).number(number).build();

        when(restaurantTableRepository.findByNumber(any(Integer.class))).thenReturn(Optional.of(restaurantTable));

        Throwable thrown = catchThrowable(() -> restaurantTableValidator
                .validateRestaurantTableForSave(restaurantTable));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("Table №%s already exists.", number), "number");
    }
}

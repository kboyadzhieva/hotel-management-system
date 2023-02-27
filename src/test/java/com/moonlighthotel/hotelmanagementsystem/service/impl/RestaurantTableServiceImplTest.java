package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.RestaurantTableBuilder;
import com.moonlighthotel.hotelmanagementsystem.enumeration.SectionType;
import com.moonlighthotel.hotelmanagementsystem.enumeration.ZoneType;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.restaurant.RestaurantTable;
import com.moonlighthotel.hotelmanagementsystem.repository.RestaurantTableRepository;
import com.moonlighthotel.hotelmanagementsystem.validator.RestaurantTableValidator;
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
public class RestaurantTableServiceImplTest {

    @Mock
    private RestaurantTableRepository restaurantTableRepository;

    @Mock
    private RestaurantTableValidator restaurantTableValidator;

    @Mock
    private RestaurantTableBuilder restaurantTableBuilder;

    @InjectMocks
    private RestaurantTableServiceImpl restaurantTableService;

    @Test
    public void verifyFindAll() {
        when(restaurantTableRepository.findAll()).thenReturn(List.of(RestaurantTable.builder().build()));
        restaurantTableService.findAll();

        verify(restaurantTableRepository, times(1)).findAll();
    }

    @Test
    public void validateThatFindAllReturnsAList() {
        when(restaurantTableRepository.findAll()).thenReturn(List.of(RestaurantTable.builder().build()));
        assertThat(restaurantTableService.findAll()).isInstanceOf(List.class);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;

        when(restaurantTableRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(RestaurantTable.builder().build()));
        restaurantTableService.findById(id);

        verify(restaurantTableRepository, times(1)).findById(id);
    }

    @Test
    public void validateThatFindByNonExistentIdReturnsRecordNotFoundException() {
        Long id = 1L;

        when(restaurantTableRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> restaurantTableService.findById(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("Table with id %d does not exist.", id));
    }

    @Test
    public void validateThatFindByIdReturnsAnInstanceOfARestaurantTable() {
        Long id = 1L;
        ZoneType zoneType = ZoneType.BAR;
        RestaurantTable restaurantTable = RestaurantTable.builder()
                .id(id).zone(zoneType).number(2).people(3).build();

        when(restaurantTableRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurantTable));
        assertThat(restaurantTableService.findById(restaurantTable.getId())).isInstanceOf(RestaurantTable.class);
    }

    @Test
    public void validateThatFindByIdReturnsARestaurantTableAndItIsNotNull() {
        Long id = 1L;
        ZoneType zoneType = ZoneType.BAR;
        RestaurantTable restaurantTable = RestaurantTable.builder()
                .id(id).zone(zoneType).number(2).people(3).build();

        when(restaurantTableRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurantTable));
        assertThat(restaurantTableService.findById(restaurantTable.getId())).isNotNull();
    }

    @Test
    public void verifySave() {
        Long id = 1L;
        ZoneType zoneType = ZoneType.BAR;
        SectionType sectionType = SectionType.NONSMOKING;
        RestaurantTable restaurantTable = RestaurantTable.builder()
                .id(id).zone(zoneType).number(2).people(3).build();

        when(restaurantTableValidator.getSectionType(any(RestaurantTable.class))).thenReturn(sectionType);
        when(restaurantTableBuilder.build(any(RestaurantTable.class), any(SectionType.class)))
                .thenReturn(restaurantTable);

        restaurantTableService.save(restaurantTable);

        verify(restaurantTableValidator, times(1)).validateRestaurantTableForSave(restaurantTable);
        verify(restaurantTableValidator, times(1)).getSectionType(restaurantTable);
        verify(restaurantTableBuilder, times(1)).build(restaurantTable, sectionType);
        verify(restaurantTableRepository, times(1)).save(restaurantTable);
    }

    @Test
    public void verifyUpdate() {
        Long id = 1L;
        ZoneType zoneType = ZoneType.BAR;
        SectionType sectionType = SectionType.NONSMOKING;
        RestaurantTable restaurantTable = RestaurantTable.builder()
                .id(id).zone(zoneType).number(2).people(3).build();

        when(restaurantTableRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurantTable));
        when(restaurantTableValidator.getSectionType(any(RestaurantTable.class))).thenReturn(sectionType);
        when(restaurantTableBuilder.build(any(Long.class), any(RestaurantTable.class), any(SectionType.class)))
                .thenReturn(restaurantTable);

        restaurantTableService.update(id, restaurantTable);

        verify(restaurantTableRepository, times(1)).findById(id);
        verify(restaurantTableValidator, times(1)).getSectionType(restaurantTable);
        verify(restaurantTableBuilder, times(1)).build(id, restaurantTable, sectionType);
        verify(restaurantTableRepository, times(1)).save(restaurantTable);
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        RestaurantTable restaurantTable = RestaurantTable.builder().id(id).build();

        when(restaurantTableRepository.findById(any(Long.class))).thenReturn(Optional.of(restaurantTable));

        restaurantTableService.deleteById(id);

        verify(restaurantTableRepository, times(1)).findById(id);
        verify(restaurantTableRepository, times(1)).deleteById(id);
    }
}

package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.RoomReservationBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.roomreservation.RoomReservation;
import com.moonlighthotel.hotelmanagementsystem.repository.RoomReservationRepository;
import com.moonlighthotel.hotelmanagementsystem.validator.RoomReservationValidator;
import com.moonlighthotel.hotelmanagementsystem.validator.RoomValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomReservationImplTest {

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private RoomValidator roomValidator;

    @Mock
    private RoomReservationValidator roomReservationValidator;

    @Mock
    private RoomReservationBuilder roomReservationBuilder;

    @InjectMocks
    private RoomReservationServiceImpl roomReservationService;

    @Test
    public void verifyFindAllByRoomId() {
        Long id = 1L;
        roomReservationService.findAllByRoomId(id);

        verify(roomReservationRepository, times(1)).findAllByRoomId(id);
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;
        Long rid = 1L;

        when(roomReservationRepository.findById(any(Long.class))).thenReturn(
                Optional.of(RoomReservation.builder().build()));
        roomReservationService.findById(id, rid);

        verify(roomValidator, times(1)).validateRoomExists(id);
        verify(roomReservationRepository, times(1)).findById(rid);
    }

    @Test
    public void validateThatNotFoundRoomReservationThrowsRecordNotFoundException() {
        Long id = 1L;
        Long rid = 1L;

        when(roomReservationRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> roomReservationService.findById(id, rid));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("Room reservation with id %d does not exist.", rid));
    }

    @Test
    public void verifyRoomReservationSave() {
        Long id = 1L;
        RoomReservation roomReservation = RoomReservation.builder().id(1L).build();

        when(roomReservationBuilder.build(any())).thenReturn(roomReservation);

        roomReservationService.save(id, roomReservation);

        verify(roomReservationValidator, times(1)).validateRoomReservation(id, roomReservation);
        verify(roomReservationBuilder, times(1)).build(roomReservation);
        verify(roomReservationRepository, times(1)).save(roomReservation);
    }

    @Test
    public void verifyRoomReservationUpdate() {
        Long id = 1L;
        Long rid = 1L;
        RoomReservation roomReservation = RoomReservation.builder().id(1L).build();

        when(roomReservationRepository.findById(any(Long.class))).thenReturn(Optional.of(roomReservation));

        when(roomReservationBuilder.build(any(Long.class), any())).thenReturn(roomReservation);

        roomReservationService.update(id, rid, roomReservation);

        verify(roomReservationRepository, times(1)).findById(rid);
        verify(roomReservationValidator, times(1)).validateRoomReservation(id, roomReservation);
        verify(roomReservationBuilder, times(1)).build(rid, roomReservation);
        verify(roomReservationRepository, times(1)).save(roomReservation);
    }

    @Test
    public void verifyRoomReservationDelete() {
        Long id = 1L;
        Long rid = 1L;
        RoomReservation roomReservation = RoomReservation.builder().id(1L).build();

        when(roomReservationRepository.findById(any(Long.class))).thenReturn(Optional.of(roomReservation));

        roomReservationService.deleteById(id, roomReservation.getId());

        verify(roomReservationRepository, times(1)).findById(rid);
        verify(roomReservationRepository, times(1)).deleteById(roomReservation.getId());
    }
}

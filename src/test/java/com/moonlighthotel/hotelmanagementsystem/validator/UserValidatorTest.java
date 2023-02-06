package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.user.User;
import com.moonlighthotel.hotelmanagementsystem.repository.UserRepository;
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
public class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    private Throwable thrown;

    @Test
    public void verifyUserExists() {
        Long id = 1L;
        User user = User.builder().build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userValidator.validateUserExists(id);

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void validateThatNonExistentUserIdThrowsRecordNotFoundException() {
        Long id = 1L;

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        thrown = catchThrowable(() -> userValidator.validateUserExists(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("User with id '%d' does not exist.", id));
    }

    @Test
    public void verifyEmail() {
        Long id = 1L;
        String email = "new@mail.com";

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        userValidator.validateEmail(email);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void validateThatDuplicateEmailThrowsDuplicateRecordException() {
        User user = User.builder().build();
        String email = "new@mail.com";

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        thrown = catchThrowable(() -> userValidator.validateEmail(email));

        assertThat(thrown)
                .isInstanceOf(DuplicateRecordException.class)
                .hasMessage(String.format("User with email '%s' already exists.", email));
    }
}

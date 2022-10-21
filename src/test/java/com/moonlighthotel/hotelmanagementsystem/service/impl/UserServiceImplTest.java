package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.UserBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.Role;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.repository.UserRepository;
import com.moonlighthotel.hotelmanagementsystem.validator.RoleValidator;
import com.moonlighthotel.hotelmanagementsystem.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleValidator roleValidator;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserBuilder userBuilder;

    @InjectMocks
    private UserServiceImpl userService;

    private Throwable thrown;

    @Test
    public void verifyFindAll() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void verifyFindById() {
        Long id = 1L;

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(User.builder().build()));
        userService.findById(id);

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void validateThatNonExistentIdOfUserThrowsRecordNotFoundException() {
        Long id = 1L;

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        thrown = catchThrowable(() -> userService.findById(id));

        assertThat(thrown)
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage(String.format("User with id '%d' does not exist.", id));
    }

    @Test
    public void verifySaveByAdmin() {
        User user = User.builder().email("new@mail.com").build();

        when(userBuilder.buildUserByAdmin(any())).thenReturn(user);

        userService.saveByAdmin(user);

        verify(userValidator, times(1)).validateEmail(user.getEmail());
        verify(userBuilder, times(1)).buildUserByAdmin(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void verifySaveByClient() {
        User user = User.builder().email("new@mail.com").build();
        Role role = Role.builder().name("client").build();

        when(userBuilder.buildUserByClient(any(), any())).thenReturn(user);
        when(roleValidator.validateRoleExists(any())).thenReturn(role);

        userService.saveByClient(user);

        verify(userValidator, times(1)).validateEmail(user.getEmail());
        verify(userBuilder, times(1)).buildUserByClient(user, role);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void verifyDeleteById() {
        Long id = 1L;
        User user = User.builder().id(id).build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        userService.deleteById(user.getId());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}

package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.UserBuilder;
import com.moonlighthotel.hotelmanagementsystem.repository.UserRepository;
import com.moonlighthotel.hotelmanagementsystem.validator.RoleValidator;
import com.moonlighthotel.hotelmanagementsystem.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void verifyFindAll() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }
}

package com.moonlighthotel.hotelmanagementsystem.service.impl;

import com.moonlighthotel.hotelmanagementsystem.builder.UserBuilder;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.Role;
import com.moonlighthotel.hotelmanagementsystem.model.User;
import com.moonlighthotel.hotelmanagementsystem.repository.UserRepository;
import com.moonlighthotel.hotelmanagementsystem.service.UserService;
import com.moonlighthotel.hotelmanagementsystem.validator.RoleValidator;
import com.moonlighthotel.hotelmanagementsystem.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleValidator roleValidator;

    @Autowired
    private final UserValidator userValidator;

    @Autowired
    private final UserBuilder userBuilder;

    private static final String ROLE_NAME = "ROLE_CLIENT";

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("User with id '%d' does not exist.", id)));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("User with email '%s' was not found.", email)));
    }

    @Override
    public User saveByAdmin(User user) {
        userValidator.validateEmail(user.getEmail());
        User userForSave = userBuilder.buildUserByAdmin(user);
        return userRepository.save(userForSave);
    }

    @Override
    public User saveByClient(User user) {
        userValidator.validateEmail(user.getEmail());
        Role role = roleValidator.validateRoleExists(ROLE_NAME);
        User userForSave = userBuilder.buildUserByClient(user, role);
        return userRepository.save(userForSave);
    }

    @Override
    public User update(Long id, User user) {
        userValidator.validateUserExists(id);
        userValidator.validateEmail(user.getEmail());
        User userForUpdate = userBuilder.buildUserForUpdate(id, user);
        return userRepository.save(userForUpdate);
    }

    @Override
    public void deleteById(Long id) {
        User foundUser = findById(id);
        userRepository.deleteById(foundUser.getId());
    }
}

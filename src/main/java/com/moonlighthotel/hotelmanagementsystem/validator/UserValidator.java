package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserValidator {

    @Autowired
    private final UserRepository userRepository;

    public void validateUserExists(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("User with id '%d' does not exist.", id)));
    }

    public void validateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateRecordException("User with email '%s' already exists.");
        }
    }
}

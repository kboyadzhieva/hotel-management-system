package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.Role;
import com.moonlighthotel.hotelmanagementsystem.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RoleValidator {

    @Autowired
    private final RoleRepository roleRepository;

    public Role validateRoleName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Role with name '%s' was not found.", name)));
    }
}

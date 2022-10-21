package com.moonlighthotel.hotelmanagementsystem.validator;

import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.model.Role;
import com.moonlighthotel.hotelmanagementsystem.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RoleValidator {

    @Autowired
    private final RoleRepository roleRepository;

    public Role validateRoleExists(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Role with name '%s' was not found.", name)));
    }

    public Set<Role> validateRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> validateRoleExists(role.getName()))
                .collect(Collectors.toSet());
    }
}

package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.model.Role;
import com.moonlighthotel.hotelmanagementsystem.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RoleConverter {

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private final RoleService roleService;

    public Set<String> substring(Set<Role> roles) {
        return roles.stream().map(role -> role.getName()
                .substring(ROLE_PREFIX.length()).toLowerCase())
                .collect(Collectors.toSet());
    }

    public Set<Role> toSetOfRoles(Set<String> roles) {
        return roles.stream()
                .map(role -> roleService.findByName(ROLE_PREFIX + role.toUpperCase()))
                .collect(Collectors.toSet());
    }
}

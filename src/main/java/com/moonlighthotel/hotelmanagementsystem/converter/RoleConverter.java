package com.moonlighthotel.hotelmanagementsystem.converter;

import com.moonlighthotel.hotelmanagementsystem.model.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleConverter {

    private static final String ROLE_PREFIX = "ROLE_";

    public Set<String> substring(Set<Role> roles) {
        return roles.stream().map(role -> role.getName()
                .substring(ROLE_PREFIX.length()).toLowerCase())
                .collect(Collectors.toSet());
    }
}

package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.user.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);
}

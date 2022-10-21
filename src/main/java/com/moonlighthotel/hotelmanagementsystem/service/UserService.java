package com.moonlighthotel.hotelmanagementsystem.service;

import com.moonlighthotel.hotelmanagementsystem.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User saveByAdmin(User user);

    User saveByClient(User user);

    User update(User user, Long id);

    void deleteById(Long id);
}

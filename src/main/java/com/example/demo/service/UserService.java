package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User update(User user);

    List<User> getAll();

    User getOne(Integer id);

    void deleteUser(Integer id);
}

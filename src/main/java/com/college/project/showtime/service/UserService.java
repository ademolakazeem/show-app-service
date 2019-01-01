package com.college.project.showtime.service;

import com.college.project.showtime.model.User;

import java.util.List;

public interface UserService {
    User saveUser(String name, User user);

    List<User> getAllAdmin();

    List<User> getAllUsers();

    User findUserByName(String name);
}

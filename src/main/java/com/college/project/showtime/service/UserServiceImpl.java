package com.college.project.showtime.service;

import com.college.project.showtime.model.User;
import com.college.project.showtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(String name, User user) {
        User userFound = userRepository.findByName(name);
        if (userFound != null && userFound.isAdmin()) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public List<User> getAllAdmin() {
        User user = new User();
        user.setAdmin(true);
        return userRepository.findAll(Example.of(user));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        if (allUsers == null || allUsers.isEmpty()) {
            return null;
        }
        return allUsers;
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }
}

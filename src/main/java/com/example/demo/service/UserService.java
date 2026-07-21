package com.example.demo.service;

import java.util.List;
import com.example.demo.models.User;

public interface UserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

}
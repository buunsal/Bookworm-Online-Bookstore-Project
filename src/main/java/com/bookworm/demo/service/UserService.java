package com.bookworm.demo.service;

import com.bookworm.demo.exception.ResourceNotFoundException;
import com.bookworm.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UserService {

    User findUserByEmail(String email);

    void updatePassword(User user);

    void updateUser(User user);

    void saveAdmin(User user);

    void registration(User user);

    @Transactional
    List<User> getUsers();

    @Transactional
    User getUser(int id) throws ResourceNotFoundException;

    @Transactional
    void deleteUser(int Id);

    void autologin(User user, String password);

}

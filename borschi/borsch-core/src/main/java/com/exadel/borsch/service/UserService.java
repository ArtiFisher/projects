package com.exadel.borsch.service;



import com.exadel.borsch.entity.User;

import java.util.List;

public interface UserService {
    List<User> list();
    User findUser(String username);
    void saveUser(User user);
    public void addUser(User user);
    void deleteUser(String id);
    public User findUserById(String id);
}
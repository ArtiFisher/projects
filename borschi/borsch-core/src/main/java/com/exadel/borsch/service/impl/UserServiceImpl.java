package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.UserDAO;
import com.exadel.borsch.entity.User;
import com.exadel.borsch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<User> list() {
        return userDAO.getUsers();
    }

    @Transactional(readOnly = true)
    public User findUser(String login) {
        return userDAO.findByLogin(login);
    }

    @Override
    public User findUserById(String id) {
        return userDAO.findById(new Integer(id));
    }

    @Override
    public void addUser(User user) {
        user.setRole("1");
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        userDAO.saveUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
        boolean isNew = user.getId() == null;

//        System.out.println("id: " +user.getId());
        System.out.println("login: " +user.getLogin());
//        System.out.println("password: " +user.getPassword());
//        System.out.println("role: " +user.getRole());
//        System.out.println("email: " +user.getEmail());
//        System.out.println("info: " +user.getInfo());
//
//        user.setRole("2");//TODO:!!!!


        if (isNew) {
            userDAO.saveUser(user);
        } else {
            userDAO.updateUser(user);
        }
    }

    @Override
    @Transactional(propagation =  Propagation.NEVER)
    public void deleteUser(String id){
        userDAO.deleteUser(new Integer(id));
    }



}

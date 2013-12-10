package com.exadel.borsch.web.controllers;

import com.exadel.borsch.entity.User;
import com.exadel.borsch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class Validator {

    @Autowired
    UserService userService;


    public boolean validateLogin(User userToCheck){

        User user = null;
        user = userService.findUser(userToCheck.getLogin());
        return (user == null) ? false : true;

    }
}

package com.exadel.borsch.web.controllers;

import com.exadel.borsch.entity.User;
import com.exadel.borsch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkUser")
public class CheckUserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String checkLogin(@RequestParam String login){

        User user = null;
        user = userService.findUser(login);
        if(user == null){
           return "This login is free to use!";
        }

        return "This login already exists!";
    }


}

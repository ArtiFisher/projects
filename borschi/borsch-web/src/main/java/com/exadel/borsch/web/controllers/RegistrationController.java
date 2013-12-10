package com.exadel.borsch.web.controllers;


import com.exadel.borsch.entity.User;
import com.exadel.borsch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Map;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

//    @Autowired
//    private RegistrationValidation registrationValidation;
//
//    public void setRegistrationValidation(RegistrationValidation registrationValidation){
//        this.registrationValidation = registrationValidation;
//    }

    private String md5Encoder(String password) {
        PasswordEncoder encoder = (PasswordEncoder) new Md5PasswordEncoder();
        return encoder.encode(password);
    }



    @RequestMapping(method = RequestMethod.GET)
    public String showRegistration(Map model) {

        User user = new User();
        model.put("user", user);
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    private String processRegistration(@Valid User user,
                                       BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }

        if(!new Validator().validateLogin(user)){
          userService.addUser(user);
        }

        return "redirect:/";
    }

}

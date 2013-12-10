package com.exadel.borsch.web.controllers;

import com.exadel.borsch.entity.User;
import com.exadel.borsch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class UserManagementController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users() {

        Map params = new HashMap();
        params.put("users", userService.list());
        return new ModelAndView("admin/users", params);
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable String id){
        userService.deleteUser(id);

        Map params = new HashMap();
        params.put("users", userService.list());
        return new ModelAndView("admin/users", params);
    }

    @RequestMapping(value = "/editUser/{id}" ,  method = RequestMethod.GET)
    public String getEditUserForm(@PathVariable String id, Map model){

        User user = userService.findUserById(id);
        model.put("user", user);
        return "admin/editUser";
    }


    @RequestMapping(value = "/editUser/{id}", method = RequestMethod.POST)
    public String postEditUserFrom(@Valid User user,
                                   BindingResult result) {

        if(result.hasErrors()) {
            return "admin/editUser";
        }

        userService.saveUser(user);
        return "redirect:/admin/users";

    }

    @RequestMapping(value = "/addUser" ,  method = RequestMethod.GET)
    public String getAddUserForm(Map model){

        User user = new User();
        model.put("user", user);
        return "admin/addUser";
    }

    @RequestMapping(value = "/addUser" , method = RequestMethod.POST)
    public String postAddUserFrom(@Valid User user,
                                   BindingResult result) {

        userService.saveUser(user);
        return "redirect:/admin/users";

    }

 }

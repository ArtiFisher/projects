package com.exadel.borsch.web.controllers;

import com.exadel.borsch.entity.User;
import com.exadel.borsch.service.DishService;
import com.exadel.borsch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple index page controller
 */
@Controller
//@RequestMapping(value = "/borsch-web")
public class IndexController {
    @Autowired
    private DishService dishService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sec/menu/dishes/{date}", method = RequestMethod.GET)

    public ModelAndView dishes(@PathVariable() String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date selectedDate;
        if (date == null) {
            selectedDate = new Date();
        } else {
            try {
                selectedDate = dateFormat.parse(date);
            } catch (ParseException e) {
                selectedDate = new Date();
            }
        }
        Map params = new HashMap();
//        params.put("dishes", dishService.list(selectedDate));
        params.put("dishes", dishService.list());
        return new ModelAndView("dishes.list", params);
    }

    @RequestMapping(value = "/sec/menu/dishes_user/{date}", method = RequestMethod.GET)
    public ModelAndView userdishes(@PathVariable String date) {
        Map params = new HashMap();
        params.put("dishes", dishService.list());
        return new ModelAndView("dishes.user", params);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model, Principal principal) {
        String name = null;
        if (principal != null) {
            name = principal.getName();
        }
        model.addAttribute("username", name);
        Map params = new HashMap();
        Date date=new Date();
        String selDate=""+(1900+date.getYear());
        if(date.getMonth()<10)
            selDate+="-0"+(date.getMonth()+1);
        else
            selDate+="-"+(date.getMonth()+1);
        if(date.getDate()<10)
            selDate+="-0"+(date.getDate());
        else
            selDate+="-"+(date.getDate());
        params.put("dishes", dishService.getProducts(selDate));
        return new ModelAndView("index", params);
    }

    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public ModelAndView indexDate(@PathVariable() String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map params = new HashMap();
        params.put("dishes", dishService.getProducts(date));
        return new ModelAndView("index.date", params);
    }

    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "enter";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "enter";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "enter";
    }

//    @RequestMapping(value = "/registration" , method = RequestMethod.GET)
//    public String registration() {
//        return "registration";
//    }
//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String addUser(@RequestParam String userName,
//                          @RequestParam String password,
//                          @RequestParam String info,
//                          @RequestParam String email,
//                          Model model) {
//        User user = new User(userName, password, info, email);
//        user.setRole(1);
//        model.addAttribute("success", "Вы зарегистрированны");
//        userService.saveUser(user);
//        return "registration";
//    }

}

package com.exadel.borsch.web.controllers;

import com.exadel.borsch.entity.Dish;
import com.exadel.borsch.service.DishInOrderService;
import com.exadel.borsch.service.DishService;
import com.exadel.borsch.service.DateService;
import com.exadel.borsch.service.OrderService;
import com.exadel.borsch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Controller

@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private DishService dishService;

    @Autowired
    private UserService userService;

    @Autowired
    private DateService dateService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishInOrderService dishInOrderService;

    @RequestMapping(value = "/adddish", method = RequestMethod.GET)
    public String showAddDishForm() {
        return "admin/showAddDishForm";
    }

    @RequestMapping(value = "/adddish", method = RequestMethod.POST)
    public String addDish(@RequestParam String name,
                          @RequestParam int price,
                          @RequestParam String info,
                          Model model) {
        Dish dish = new Dish(name, price, info);
        dish.setImg("default.jpg");
        model.addAttribute("success", "Блюдо добавлено");
        dishService.saveDish(dish);
        return "/enter";
    }

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public ModelAndView dishes() {
        Map params = new HashMap();
        params.put("dishes", dishService.list());
        return new ModelAndView("dishes.list", params);
    }

        @RequestMapping(value = "/deleteDish/{id}",method = RequestMethod.POST)
    public ModelAndView deleteDish (@PathVariable String id){
        dishService.deleteDish(id);

        Map params = new HashMap();
        params.put("dishes", dishService.list());
        return new ModelAndView("redirect:dishes.list", params);
    }

    @RequestMapping(value = "/editDish/{id}",method = RequestMethod.GET)
    public ModelAndView showEditDish (@PathVariable String id ){
        Dish dish = dishService.findDishById(id);
        Map model = new HashMap();
        model.put("dish", dish);
        return new ModelAndView("admin/editDish",model);
    }

    @RequestMapping(value = "/editDish/{id}",method = RequestMethod.POST)
    public String updateEditDish (@PathVariable String id,
                                  @RequestParam String name,
                                  @RequestParam Integer price,
                                  @RequestParam String info,
                                  Model model) {

        Dish dish = dishService.findDishById(id);
        dish.setName(name);
        dish.setPrice(price);
        dish.setInfo(info);
        dishService.updateDish(dish);
        return "/enter";
    }

    @RequestMapping(value = "/markdish/{date}", method = RequestMethod.GET)
    public String markDish(ModelMap model, @PathVariable() String date) {
        String selectedDate = dateService.getDateByString(date);
        model.put("date", selectedDate);
        model.put("list", dishService.getProductsForAdmin(selectedDate));
        return "markDish";
    }

    @RequestMapping(value = "/markdish", method = RequestMethod.GET)
    public String markDishGet(ModelMap model) {
        model.put("date", dateService.getCurrentDate());
        model.put("list", dishService.getProductsForAdmin(dateService.getCurrentDate()));
        return "markDish";
    }


    @RequestMapping(value = "/markdish", method = RequestMethod.POST)
    public String markDishPost(WebRequest request, ModelMap model) {
        Map map = new HashMap();
        Iterator<String> it = request.getParameterNames();
        while (it.hasNext()) {
            String next = it.next();
            if (next.matches("[0-9]+")) {
                map.put(next, request.getParameter(next));
//                System.out.println(next + " " + request.getParameter(next));
            }
        }
        dishService.markDish(map, request.getParameter("date"));
        model.put("date", dateService.getCurrentDate());
        model.put("list", dishService.getProductsForAdmin(dateService.getCurrentDate()));
        return "markDish";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ModelAndView orders() {
        Map params = new HashMap();
        params.put("orders", orderService.list());
        return new ModelAndView("admin.orders", params);
    }

    @RequestMapping(value = "/orders/{date}", method = RequestMethod.GET)
    public ModelAndView ordersDate(@PathVariable() String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
        params.put("orders", orderService.list(selectedDate));
        return new ModelAndView("admin.orders", params);
    }

    @RequestMapping(value = "/cancels", method = RequestMethod.GET)
    public ModelAndView cancels() {
        Map params = new HashMap();
        params.put("orders", orderService.cancels());
        return new ModelAndView("admin.cancels", params);
    }

    @RequestMapping(value = "/cancels/{date}", method = RequestMethod.GET)
    public ModelAndView cancelsDate(@PathVariable() String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
        params.put("orders", orderService.cancels(selectedDate));
        return new ModelAndView("admin.cancels", params);
    }


}



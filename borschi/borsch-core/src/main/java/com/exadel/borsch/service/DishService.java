package com.exadel.borsch.service;

import com.exadel.borsch.entity.Dish;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DishService {
    List<Dish> list();
    //List<Dish> list(Date selectedDate);
    void saveDish(Dish dish);
    List<Dish> getProducts();
    List<Dish> getProductsForAdmin(String date);
    void updateDish(Dish dish);
    void markDish(Map map, String date);
    List<Dish> getProducts(String date);
    void deleteDish (String id);
    Dish findDishById(String id);


}

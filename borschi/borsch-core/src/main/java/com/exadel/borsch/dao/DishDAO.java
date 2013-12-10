package com.exadel.borsch.dao;

import com.exadel.borsch.entity.Dish;

import java.util.Date;
import java.util.List;

public interface DishDAO {

    List<Dish> getProducts();
    Dish findById(Integer id);
    void saveDish(Dish dish);
    void deleteDish(Integer id);
    void updeteDish(Dish dish);
    List<Dish> getProducts(String selectedDate);
    List<Dish> getProductsForAdmin(String selectedDate);
    List<Integer> getDishId();
    void insertDishMark(String date, int dish_id);
    void deleteDishMark(String date, int dish_id);
    int getDishAccessCount(String date, int dish_id);
    void deleteDishFromDishAccess (Integer id);


}

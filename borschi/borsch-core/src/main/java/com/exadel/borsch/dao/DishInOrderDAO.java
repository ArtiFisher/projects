package com.exadel.borsch.dao;

import com.exadel.borsch.entity.DishInOrder;

import java.util.List;

public interface DishInOrderDAO {

    List<DishInOrder> getDishesInOrder();
    public List<DishInOrder> getDishesInOrderById(Integer id);
    public void deleteOrder(Integer id);
    //List<Dish> getProducts(Date selectedDate);
}

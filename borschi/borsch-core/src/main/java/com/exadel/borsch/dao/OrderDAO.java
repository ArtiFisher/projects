package com.exadel.borsch.dao;

import com.exadel.borsch.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderDAO {

    List<Order> getAllOrders();
    List<Order> getAllCancels();
    Order findById(Integer id);
    List<Order> getOrdersByDate(Date selectedDate);
    List<Order> getCancelsByDate(Date selectedDate);
    List<Order> getUserDishesByDate(Integer id, Date selectedDate);
    List<Order> getDishesByDate(Date selectedDate);
}

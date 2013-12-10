package com.exadel.borsch.service;

import com.exadel.borsch.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> list();
    List<Order> list(Date selectedDate);
    List<Order> cancels();
    List<Order> cancels(Date selectedDate);
}

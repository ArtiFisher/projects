package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.OrderDAO;
import com.exadel.borsch.entity.Order;
import com.exadel.borsch.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO orderDAO;

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<Order> list() {
        return orderDAO.getAllOrders();
    }

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<Order> list(Date selectedDate) {
        return orderDAO.getOrdersByDate(selectedDate);
    }
    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<Order> cancels() {
        return orderDAO.getAllCancels();
    }

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<Order> cancels(Date selectedDate) {
        return orderDAO.getCancelsByDate(selectedDate);
    }



}
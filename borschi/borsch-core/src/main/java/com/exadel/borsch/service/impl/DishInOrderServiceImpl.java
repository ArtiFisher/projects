package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.DishInOrderDAO;
import com.exadel.borsch.entity.DishInOrder;
import com.exadel.borsch.service.DishInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishInOrderServiceImpl implements DishInOrderService {
    @Autowired
    private DishInOrderDAO dishInOrderDAO;

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<DishInOrder> list() {
        return dishInOrderDAO.getDishesInOrder();
    }


}
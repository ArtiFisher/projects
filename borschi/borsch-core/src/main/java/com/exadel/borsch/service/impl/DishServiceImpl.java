package com.exadel.borsch.service.impl;

import com.exadel.borsch.dao.DishDAO;
import com.exadel.borsch.dao.DishInOrderDAO;
import com.exadel.borsch.entity.Dish;
import com.exadel.borsch.entity.DishInOrder;
import com.exadel.borsch.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DishServiceImpl implements DishService {
    @Autowired
    private DishDAO dishDAO;

    @Autowired
    private DishInOrderDAO dishInOrderDAO;

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public List<Dish> list() {
        return dishDAO.getProducts();
    }

    @Override
    public List<Dish> getProductsForAdmin(String date) {
        return dishDAO.getProductsForAdmin(date);
    }

//    @Override
//    public List<Dish> list(Date selectedDate) {
//        return null;
//    }

//    @Transactional(propagation = Propagation.NEVER, readOnly = true)
//    @Override
//    public List<Dish> list(Date selectedDate) {
//        return dishDAO.getProducts(selectedDate);
//    }

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    @Override
    public void saveDish(Dish dish) {
        dishDAO.saveDish(dish);
    }

    @Override
    public List<Dish> getProducts() {
        return dishDAO.getProducts();
    }

    @Override
    public void updateDish(Dish dish) {
        dishDAO.updeteDish(dish);
    }

    @Override
    public void markDish(Map map, String date) {
        List<Integer> id = dishDAO.getDishId();
        for (int i = 0; i < id.size(); ++i) {
            String selectedID = id.get(i).toString();
            if (map.containsKey(selectedID) && map.get(selectedID).equals("1")) {
                if (dishDAO.getDishAccessCount(date, id.get(i)) == 0) {
                    dishDAO.insertDishMark(date, id.get(i));
    }
            } else {
                dishDAO.deleteDishMark(date, id.get(i));
            }
        }
    }

    @Override
    public List<Dish> getProducts(String date) {
        return dishDAO.getProducts(date);
    }

    @Override
    @Transactional(propagation =  Propagation.NEVER)
    public void deleteDish(String id) {
        List<DishInOrder> dishesInOrderById = dishInOrderDAO.getDishesInOrderById(new Integer(id));
        if(!dishesInOrderById.isEmpty()) {
           dishInOrderDAO.deleteOrder(new Integer(id));
        }
        dishDAO.deleteDishFromDishAccess(new Integer(id));
        dishDAO.deleteDish(new Integer(id));
    }

    @Override
    public Dish findDishById(String id) {
        return dishDAO.findById(new Integer(id));
    }

}
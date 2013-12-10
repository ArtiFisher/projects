package com.exadel.borsch.entity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private Integer userId;
    private Date data;
    private Integer cancelled;
    private List<DishInOrder> dishes;
    private String userLogin;

    public Order() {
    }

    public Order(Integer id, Integer userId, Date data, Integer cancelled, List<DishInOrder> list ) {
        this.id = id;
        this.userId = userId;
        this.data = data;
        this.cancelled=cancelled;
        this.dishes=list;

    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCancelled() {
        return cancelled;
    }

    public void setCancelled(Integer cancelled) {
        this.cancelled = cancelled;
    }

    public List<DishInOrder> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishInOrder> dishes) {
        this.dishes = dishes;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }


}

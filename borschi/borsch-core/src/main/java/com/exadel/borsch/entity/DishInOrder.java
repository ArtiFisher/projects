package com.exadel.borsch.entity;

public class DishInOrder {

    private Integer dishInOrderId;
    private Integer dishId;
    private Integer onumber;
    private Integer oorder;
    private String dishName;

    public DishInOrder(Integer dishId, Integer onumber, Integer oorder) {
        this.dishId = dishId;
        this.onumber = onumber;
        this.oorder = oorder;
    }

    public DishInOrder(Integer onumber, Integer oorder, String dishName) {
        this.onumber = onumber;
        this.oorder = oorder;
        this.dishName = dishName;
    }

    public DishInOrder(Integer dishInOrderId, Integer dishId, Integer onumber, Integer oorder) {
        this.dishInOrderId = dishInOrderId;
        this.dishId = dishId;
        this.onumber = onumber;
        this.oorder = oorder;
    }


    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getDishInOrderId() {
        return dishInOrderId;
    }

    public void setDishInOrderId(Integer dishInOrderId) {
        this.dishInOrderId = dishInOrderId;
    }

    public Integer getOnumber() {
        return onumber;
    }

    public void setOnumber(Integer onumber) {
        this.onumber = onumber;
    }

    public Integer getOorder() {
        return oorder;
    }

    public void setOorder(Integer oorder) {
        this.oorder = oorder;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

}

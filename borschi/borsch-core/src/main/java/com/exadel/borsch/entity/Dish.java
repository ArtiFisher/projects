package com.exadel.borsch.entity;

public class Dish {
    private Integer id;
    private String name;
    private Integer price;
    private String info;
    private String img;
    private int dateAccess;


    public Dish(Integer id, String name, Integer price, String info, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.img = img;
    }

    public Dish(Integer id, String name, Integer price, String info, String img, int dateAccess) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.img = img;
        this.dateAccess = dateAccess;
    }

    public Dish() {
    }

    public Dish(String name, Integer price, String info, String img) {
        this.name = name;
        this.price = price;
        this.info = info;
        this.img = img;
    }
    public Dish(String name, Integer price, String info) {
        this.name = name;
        this.price = price;
        this.info = info;
    }

    public int getDateAccess() {
        return dateAccess;
    }

    public void setDateAccess(int dateAccess) {
        this.dateAccess = dateAccess;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

package com.noname.minimalstylesample.model;

public class CartAddModel {

    private String name;
    private int price;
    private String userid;
    private String mode;

    public CartAddModel(String name, int price, String userid, String mode) {
        this.name = name;
        this.price = price;
        this.userid = userid;
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getUserid() {
        return userid;
    }

    public String isMode() {
        return mode;
    }

}

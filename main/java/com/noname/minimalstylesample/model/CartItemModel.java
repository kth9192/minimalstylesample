package com.noname.minimalstylesample.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartItemModel {

    @PrimaryKey
    @NonNull
    private String name;
    private int total;
    private int totalPrice;
    private int price;

    public CartItemModel(String name, int total, int totalPrice, int price) {
        this.name = name;
        this.total = total;
        this.totalPrice = totalPrice;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPrice() {
        return price*total;
    }

    public int getPrice() {
        return price;
    }
}

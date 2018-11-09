package com.noname.minimalstylesample.model;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class CartDBModel {

    @NonNull
    private String id;
    private String user;
    private int cartPrice;
    private ArrayList<CartItemModel> items;

    public CartDBModel(@NonNull  String id, String user, int cartPrice, ArrayList<CartItemModel> items) {
        this.id = id;
        this.user = user;
        this.cartPrice = cartPrice;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getCartPrice() {
        return cartPrice;
    }

    public ArrayList<CartItemModel> getItems() {
        return items;
    }

}

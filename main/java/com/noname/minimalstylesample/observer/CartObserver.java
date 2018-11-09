package com.noname.minimalstylesample.observer;

import android.util.Log;

import com.noname.minimalstylesample.model.CartDBModel;
import com.noname.minimalstylesample.model.CartItemModel;
import com.noname.minimalstylesample.retrofit.NetRetrofit;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class CartObserver extends BaseObservable {

    private static String TAG = CartObserver.class.getSimpleName();
    private String coverImg;
    private String name;
    private int total;
    private int price;
    private int totalPrice;

    public CartObserver(CartItemModel cartItemModel) {
        this.coverImg = NetRetrofit.getInstance().getUrl() +"/image?name="+ cartItemModel.getName();
        this.name = cartItemModel.getName();
        this.total = cartItemModel.getTotal();
        this.price = cartItemModel.getPrice();
        this.totalPrice = cartItemModel.getTotalPrice();
    }

    @Bindable
    public String getCoverImg() {
        return coverImg;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public int getPrice() {
        return price;
    }

    @Bindable
    public int getTotal() {
        return total;
    }

    @Bindable
    public String getTotalTxt(){
        return String.valueOf(total);
    }

    @Bindable
    public String getTotalPriceTxt(){
        return "총 가격 : " + (price * total) + "원";
    }

    @Bindable
    public String getPriceTxt(){
        return String.valueOf(price) + "원";
    }
}

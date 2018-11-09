package com.noname.minimalstylesample.observer;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class CartActivityObserver extends BaseObservable {

    private int totalPrice;
    private int discount;

    public CartActivityObserver(int totalPrice, int discount) {
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    @Bindable
    public String getTotalPriceTxt() {
        return String.valueOf(totalPrice);
    }

    @Bindable
    public String getDiscountTxt() {
        return discount + "%";
    }

    @Bindable
    public String getResultTxt() {
        return String.valueOf((totalPrice - totalPrice*(discount/100)));
    }
}

package com.noname.minimalstylesample.observer;

import com.noname.minimalstylesample.model.ItemRoom;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class PriceObserver extends BaseObservable {

    private int price;

    public PriceObserver(ItemRoom itemRoom) {
        this.price = itemRoom.getPrice();
    }

    @Bindable
    public int getPrice(){
        return price;
    }

    @Bindable
    public String getPriceTxt() {
        return String.valueOf(getPrice());
    }

}

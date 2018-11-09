package com.noname.minimalstylesample.observer;

import android.app.Activity;

import com.noname.minimalstylesample.repository.MenuRoom;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class MenuObserver extends BaseObservable {

    private String itemImg;
    private String title;

    public MenuObserver(MenuRoom menuRoom) {
        this.itemImg = menuRoom.getItemImg();
        this.title = menuRoom.getTitle();
    }

    @Bindable
    public String getItemImg() {
        return itemImg;
    }

    @Bindable
    public String getTitle() {
        return title;
    }
}

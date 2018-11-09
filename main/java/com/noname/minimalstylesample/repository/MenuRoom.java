package com.noname.minimalstylesample.repository;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MenuRoom {

    @NonNull
    @PrimaryKey
    private String title;

    private String itemImg;

    public MenuRoom(@NonNull String title, String itemImg) {
        this.title = title;
        this.itemImg = itemImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

}

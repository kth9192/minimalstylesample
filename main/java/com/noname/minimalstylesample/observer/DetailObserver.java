package com.noname.minimalstylesample.observer;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class DetailObserver extends BaseObservable {

    private String coverImg;
    private String title;

    public DetailObserver(String coverImg, String title) {
        this.coverImg = coverImg;
        this.title = title;
    }

    @Bindable
    public String getCoverImg() {
        return coverImg;
    }

    @Bindable
    public String getTitle() {
        return title;
    }
}

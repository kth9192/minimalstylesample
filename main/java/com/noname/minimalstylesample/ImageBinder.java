package com.noname.minimalstylesample;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import androidx.databinding.BindingAdapter;

/**
 * Created by kth919 on 2017-06-06.
 */

public class ImageBinder {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String resID){
        Glide.with(imageView.getContext()).load(resID).into(imageView);
//        Log.d("확인" , resID);
    }

    @BindingAdapter({"imageInt"})
    public static void loadImage(ImageView imageView, int resID){
        Glide.with(imageView.getContext()).load(resID).into(imageView);
    }

}

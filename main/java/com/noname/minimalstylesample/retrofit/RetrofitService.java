package com.noname.minimalstylesample.retrofit;

import com.google.gson.JsonArray;
import com.noname.minimalstylesample.model.CartAddModel;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("items")
    Call<JsonArray> getAllItem();

    @GET("cart")
    Call<JsonArray> getCartItem(@Query("user") String id);

    @GET("menu")
    Call<JsonArray> getMenuInfo(@Query("name") String name);

//    @FormUrlEncoded
//    @POST("cart/add")
//    Call<CartAddModel> doAddCart(
//            @Field("name") String name,
//            @Field("price") Integer price,
//            @Field("user") String user,
//            @Field("mode") Boolean mode);

    @FormUrlEncoded
    @POST("cart/modify")
    Call<CartAddModel> modifyCart(
            @Field("name") String name,
            @Field("price") Integer price,
            @Field("user") String user,
            @Field("mode") String mode);
    }

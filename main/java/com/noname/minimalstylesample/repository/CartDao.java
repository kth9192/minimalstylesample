package com.noname.minimalstylesample.repository;

import com.noname.minimalstylesample.model.CartItemModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CartItemModel cartItemModel);

    @Query("DELETE FROM CartItemModel")
    void deleteAll();

    @Query("UPDATE CartItemModel SET total = :total WHERE name = :name")
    void updateCart(String name , int total);

    @Query("SELECT * from CartItemModel")
    LiveData<List<CartItemModel>> getAll();
}

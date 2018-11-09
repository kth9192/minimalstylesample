package com.noname.minimalstylesample.repository;

import com.noname.minimalstylesample.model.ItemRoom;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ItemRoom... itemRooms);

    @Query("DELETE FROM ItemRoom")
    void deleteAll();

    @Query("SELECT * from ItemRoom")
    LiveData<List<ItemRoom>> getAll();
}
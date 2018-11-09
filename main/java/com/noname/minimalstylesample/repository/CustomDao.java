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
public interface CustomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MenuRoom menuRooms);

    @Query("DELETE FROM MenuRoom")
    void deleteAll();

    @Query("SELECT * from MenuRoom")
    LiveData<List<MenuRoom>> getAll();
}

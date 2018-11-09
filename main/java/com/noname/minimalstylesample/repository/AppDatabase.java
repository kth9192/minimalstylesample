package com.noname.minimalstylesample.repository;

import android.content.Context;

import com.noname.minimalstylesample.model.CartItemModel;
import com.noname.minimalstylesample.model.ItemRoom;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MenuRoom.class, ItemRoom.class, CartItemModel.class}, version = 1, exportSchema = false)
@TypeConverters({CustomTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomDao customDao();
    public abstract ItemDao itemDao();
    public abstract CartDao cartDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database")
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}

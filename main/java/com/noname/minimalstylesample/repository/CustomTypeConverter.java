package com.noname.minimalstylesample.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class CustomTypeConverter {

    @TypeConverter
    public static List<MenuRoom> stringToClockModelList(String data) {

        Type listType = new TypeToken<List<MenuRoom>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String clockModelListToString(List<MenuRoom> someObjects) {

        Gson gson = new Gson();
        String json = gson.toJson(someObjects);

        return json;
    }

}

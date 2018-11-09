package com.noname.minimalstylesample.viewmodel;

import android.app.Application;


import com.noname.minimalstylesample.repository.CustomRepository;
import com.noname.minimalstylesample.repository.MenuRoom;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CustomViewModel extends AndroidViewModel {

    private CustomRepository customRepository;
    private LiveData<List<MenuRoom>> listLiveData;

    public CustomViewModel(@NonNull Application application) {
        super(application);
        customRepository = new CustomRepository(application);
    }

    public LiveData<List<MenuRoom>> getListLiveData() {
        if (listLiveData == null){
            listLiveData = customRepository.getAll();
        }
        return listLiveData;
    }

    public void insert(MenuRoom menuRoom) {customRepository.insert(menuRoom); }

    public void deleteAll(){customRepository.deleteAll();}
}

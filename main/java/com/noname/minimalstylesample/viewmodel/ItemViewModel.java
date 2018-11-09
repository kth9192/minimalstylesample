package com.noname.minimalstylesample.viewmodel;

import android.app.Application;

import com.noname.minimalstylesample.model.ItemRoom;
import com.noname.minimalstylesample.repository.CustomRepository;
import com.noname.minimalstylesample.repository.MenuRoom;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class ItemViewModel extends AndroidViewModel {

    private CustomRepository customRepository;
    private LiveData<List<ItemRoom>> listLiveData;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        customRepository = new CustomRepository(application);
    }

    public LiveData<List<ItemRoom>> getListLiveData() {
        if (listLiveData == null){
            listLiveData = customRepository.getDetailItem();
        }
        return listLiveData;
    }

    public void insert(ItemRoom itemRoom) {customRepository.insertDetail(itemRoom); }

    public void deleteAll(){customRepository.deleteDetail();}


}

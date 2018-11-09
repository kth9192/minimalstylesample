package com.noname.minimalstylesample.viewmodel;

import android.app.Application;

import com.noname.minimalstylesample.model.CartItemModel;
import com.noname.minimalstylesample.repository.CustomRepository;
import com.noname.minimalstylesample.repository.MenuRoom;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CartViewModel extends AndroidViewModel {

    private CustomRepository customRepository;
    private LiveData<List<CartItemModel>> listLiveData;

    public CartViewModel(@NonNull Application application) {
        super(application);
        customRepository = new CustomRepository(application);
    }

    public LiveData<List<CartItemModel>> getListLiveData() {
        if (listLiveData == null){
            listLiveData = customRepository.getCartItem();
        }
        return listLiveData;
    }

    public void insert(CartItemModel cartItemModel) {customRepository.insertCart(cartItemModel); }

    public void deleteAll(){customRepository.deleteCart();}

    public void update(CartItemModel cartItemModel){customRepository.updateCart(
            cartItemModel.getName(), cartItemModel.getTotal());}
}

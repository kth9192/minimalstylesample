package com.noname.minimalstylesample.repository;

import android.app.Application;

import com.noname.minimalstylesample.model.CartItemModel;
import com.noname.minimalstylesample.model.ItemRoom;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class CustomRepository {

    private CustomDao customDao;
    private ItemDao itemDao;
    private CartDao cartDao;
    private ExecutorService executorService;

    public CustomRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        customDao = db.customDao();
        itemDao = db.itemDao();
        cartDao = db.cartDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<MenuRoom>> getAll(){
        return customDao.getAll();
    }

    public LiveData<List<ItemRoom>> getDetailItem(){
        return itemDao.getAll();
    }

    public LiveData<List<CartItemModel>> getCartItem(){
        return cartDao.getAll();
    }

    public void insert(final MenuRoom menuRoom) {
        executorService.execute(() -> customDao.insert(menuRoom));
    }

    public void deleteAll(){
        executorService.execute(() -> customDao.deleteAll());
    }

    public void insertDetail(final ItemRoom itemRoom) {
        executorService.execute(() -> itemDao.insert(itemRoom));
    }

    public void deleteDetail() {
        executorService.execute(() -> itemDao.deleteAll());
    }

    public void insertCart(final CartItemModel cartItemModel) {
        executorService.execute(() -> cartDao.insert(cartItemModel));
    }

    public void deleteCart() {
        executorService.execute(() -> cartDao.deleteAll());
    }

    public void updateCart(String name , int total) {
        executorService.execute(() -> cartDao.updateCart(name, total));
    }

}

package com.noname.minimalstylesample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.noname.minimalstylesample.databinding.ActivityCartBinding;
import com.noname.minimalstylesample.model.CartItemModel;
import com.noname.minimalstylesample.model.CartDBModel;
import com.noname.minimalstylesample.observer.CartActivityObserver;
import com.noname.minimalstylesample.retrofit.NetRetrofit;
import com.noname.minimalstylesample.viewmodel.CartViewModel;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private static String TAG = CartActivity.class.getSimpleName();
    private ActivityCartBinding activityCartBinding;
    private static final Gson gson = new Gson();
    private CartActivityObserver cartActivityObserver;
    private CartViewModel cartViewModel;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        activityCartBinding.setLifecycleOwner(this);

        setSupportActionBar(activityCartBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        activityCartBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.super.onBackPressed();
            }
        });

        Call<JsonArray> res = NetRetrofit.getInstance().getService().getCartItem("tester");
        res.enqueue(new Callback<JsonArray>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                JsonArray tmp = response.body();
                CartDBModel[] cartDBModels = gson.fromJson(tmp.toString(), CartDBModel[].class);
                ArrayList<CartItemModel> source = cartDBModels[0].getItems();

                Log.d(TAG, "확인" + cartDBModels[0].getUser());

                for (int i = 0; i<source.size(); i++){
                    cartViewModel.insert(source.get(i));
                }

                CartAdapter cartAdapter = new CartAdapter(cartViewModel);
                cartAdapter.setHasStableIds(true);

                activityCartBinding.recycler.setAdapter(cartAdapter);
                activityCartBinding.recycler.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
                cartViewModel.getListLiveData().observe(CartActivity.this, cartItemModels -> {
                    cartAdapter.submitList(cartItemModels);

                    int total = 0;
                    for (int i = 0; i<cartItemModels.size(); i++){
                        total += cartItemModels.get(i).getTotal()*cartItemModels.get(i).getPrice();
                    }

                    cartActivityObserver = new CartActivityObserver(total, 0);
                    activityCartBinding.setViewModel(cartActivityObserver);
                });

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        cartViewModel.deleteAll();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartViewModel.deleteAll();
    }
}

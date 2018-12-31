package com.noname.minimalstylesample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.noname.minimalstylesample.databinding.ActivityMainBinding;
import com.noname.minimalstylesample.repository.MenuRoom;
import com.noname.minimalstylesample.retrofit.NetRetrofit;
import com.noname.minimalstylesample.view.CartActivity;
import com.noname.minimalstylesample.view.MenuAdapter;
import com.noname.minimalstylesample.viewmodel.CustomViewModel;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding activityMainBinding;
    private CustomViewModel viewModel;
    private Gson gson = new GsonBuilder().create();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(CustomViewModel.class);

        setSupportActionBar(activityMainBinding.toolbar);

        Call<JsonArray> getAllList = NetRetrofit.getInstance().getService().getAllItem();
        getAllList.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                Log.d(TAG, response.body().toString());

                MenuRoom[] mainModels = gson.fromJson(response.body().toString(), MenuRoom[].class);
                List<MenuRoom> list = Arrays.asList(mainModels);

                for (MenuRoom tmpRoom : list) {
                    viewModel.insert(tmpRoom);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        MenuAdapter menuAdapter = new MenuAdapter(this);
        activityMainBinding.recycler.setAdapter(menuAdapter);
        activityMainBinding.recycler.setLayoutManager(new GridLayoutManager(this, 2));

        viewModel.getListLiveData().observe(this, menuRooms -> menuAdapter.submitList(menuRooms));

        activityMainBinding.cart.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext() , CartActivity.class);
            startActivity(intent);

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}

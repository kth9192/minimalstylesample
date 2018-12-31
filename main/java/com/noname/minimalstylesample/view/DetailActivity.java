package com.noname.minimalstylesample.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.noname.minimalstylesample.R;
import com.noname.minimalstylesample.databinding.ActivityDetailBinding;
import com.noname.minimalstylesample.model.CartAddModel;
import com.noname.minimalstylesample.model.ItemRoom;
import com.noname.minimalstylesample.retrofit.NetRetrofit;
import com.noname.minimalstylesample.observer.DetailObserver;
import com.noname.minimalstylesample.viewmodel.ItemViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {

    private static String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding activityDetailBinding;
    private ItemViewModel itemViewModel;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        setSupportActionBar(activityDetailBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        activityDetailBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.super.onBackPressed();
            }
        });

        String cover = getIntent().getStringExtra("cover");
        final String title = getIntent().getStringExtra("name");
        final int price = getIntent().getIntExtra("price", 0);

        Log.d(TAG, "title : "  + title + " price : " + price);

        activityDetailBinding.setViewModel(new DetailObserver(cover, title));
        if (savedInstanceState == null) {
            itemViewModel.insert(new ItemRoom(title, price));
        }

        ItemDetailAdapter itemDetailAdapter = new ItemDetailAdapter();
        activityDetailBinding.recycler.setAdapter(itemDetailAdapter);
        activityDetailBinding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        itemViewModel.getListLiveData().observe(this, itemRooms -> {
            itemDetailAdapter.submitList(itemRooms);
            Log.d(TAG,"사이즈확인" + itemDetailAdapter.getItemCount()  );
        });

        ArrayList tmp = new ArrayList();
        tmp.add(new SpeedDialActionItem.Builder(R.id.fab_addcart,
                R.drawable.add_shopping_cart_black_24dp).create());
        tmp.add(new SpeedDialActionItem.Builder(R.id.fab_direct, R.drawable.delivery).create());

        activityDetailBinding.confirm.addAllActionItems(tmp);
        activityDetailBinding.confirm.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()){
                    case R.id.fab_no_label:
                        Toast.makeText(getApplicationContext(), "낄낄", Toast.LENGTH_LONG);
                        return true;

                    case R.id.fab_addcart:
                    Call<CartAddModel> send = NetRetrofit.getInstance().getService().modifyCart(title, price, "tester", "true");
                    send.enqueue(new Callback<CartAddModel>() {
                        @Override
                        public void onResponse(Call<CartAddModel> call, Response<CartAddModel> response) {
                            Log.d(TAG, "retrofit  " + response.message());
                            DetailActivity.super.onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<CartAddModel> call, Throwable t) {
                            Log.e(TAG, "retrofit error " + t.getMessage());

                        }
                    });
                        return false;

                    case R.id.direct:
                        return false;

                    default:
                        return false;
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        itemViewModel.deleteAll();

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
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

}

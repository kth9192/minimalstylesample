package com.noname.minimalstylesample.view;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.noname.minimalstylesample.R;
import com.noname.minimalstylesample.databinding.RecyclerMenuitemBinding;
import com.noname.minimalstylesample.model.ItemRoom;
import com.noname.minimalstylesample.repository.MenuRoom;
import com.noname.minimalstylesample.retrofit.NetRetrofit;
import com.noname.minimalstylesample.observer.MenuObserver;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAdapter extends ListAdapter<MenuRoom, RecyclerView.ViewHolder> {

    private static String TAG = MenuAdapter.class.getSimpleName();
    private int VIEW_TYPE_LOADING = 0;
    private int VIEW_TYPE_ITEM =1 ;

    private Activity activity;
    private Gson gson = new Gson();

    public MenuAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menuitem, parent, false);
        return new BindingHolder(v);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof BindingHolder) {
            ((BindingHolder) holder).getRecyclerMenuitemBinding().setViewModel(new MenuObserver(getItem(position)));
            ((BindingHolder) holder).getRecyclerMenuitemBinding().executePendingBindings();

            ((BindingHolder) holder).getRecyclerMenuitemBinding().cover.bringToFront();
            ((BindingHolder) holder).getRecyclerMenuitemBinding().cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra("cover", getItem(position).getItemImg());

                    Call<JsonArray> getMenu = NetRetrofit.getInstance().getService().getMenuInfo(getItem(position).getTitle());
                    getMenu.enqueue(new Callback<JsonArray>() {
                        @Override
                        public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                            ItemRoom[] tmp = gson.fromJson(response.body().toString(), ItemRoom[].class);
                            List<ItemRoom> list = Arrays.asList(tmp);

                            for (ItemRoom itemRoom : list) {
                                Log.d(TAG,"NAME " + itemRoom.getName() + " PRICE " + itemRoom.getPrice() );
                                intent.putExtra("name", itemRoom.getName());
                                intent.putExtra("price", itemRoom.getPrice());
                            }

                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                                    ((BindingHolder) holder).getRecyclerMenuitemBinding().cover, "cover");
                            activity.startActivity(intent, optionsCompat.toBundle());
                        }

                        @Override
                        public void onFailure(Call<JsonArray> call, Throwable t) {
                            Log.e(TAG, t.getMessage());
                        }
                    });

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemImg() == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public static final DiffUtil.ItemCallback<MenuRoom> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MenuRoom>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull MenuRoom oldModel, @NonNull MenuRoom newModel) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return (oldModel.getTitle().equals(newModel.getTitle()));
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull MenuRoom oldModel, @NonNull MenuRoom newModel) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldModel.equals(newModel);
                }
            };

    static class BindingHolder extends RecyclerView.ViewHolder {

        private RecyclerMenuitemBinding recyclerMenuitemBinding;

        BindingHolder(View itemView) {
            super(itemView);
            recyclerMenuitemBinding = DataBindingUtil.bind(itemView);
        }

        public RecyclerMenuitemBinding getRecyclerMenuitemBinding() {
            return recyclerMenuitemBinding;
        }
    }
}

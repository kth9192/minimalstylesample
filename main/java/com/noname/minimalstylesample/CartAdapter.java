package com.noname.minimalstylesample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.minimalstylesample.databinding.RecyclerCartitemBinding;
import com.noname.minimalstylesample.model.CartAddModel;
import com.noname.minimalstylesample.model.CartItemModel;
import com.noname.minimalstylesample.retrofit.NetRetrofit;
import com.noname.minimalstylesample.observer.CartObserver;
import com.noname.minimalstylesample.viewmodel.CartViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends ListAdapter<CartItemModel, CartAdapter.BindingHolder> {

    private static String TAG = CartAdapter.class.getSimpleName();
    private CartViewModel cartViewModel;

    protected CartAdapter(CartViewModel cartViewModel) {
        super(DIFF_CALLBACK);
        this.cartViewModel = cartViewModel;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cartitem, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BindingHolder holder, final int position) {

        holder.getRecyclerCartitemBinding().setViewModel(new CartObserver(getItem(position)));
        holder.getRecyclerCartitemBinding().executePendingBindings();

        holder.getRecyclerCartitemBinding().plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int preCnt  = Integer.parseInt(holder.getRecyclerCartitemBinding().counter.getText().toString());
                int result = preCnt + 1;
                Log.d(TAG, String.valueOf(result));

                cartViewModel.update(new CartItemModel(getItem(position).getName(),
                        result, getItem(position).getPrice()*result , getItem(position).getPrice()));

                Call<CartAddModel> send = NetRetrofit.getInstance().getService().modifyCart(getItem(position).getName(),
                        getItem(position).getPrice(), "tester", "true");
                send.enqueue(new Callback<CartAddModel>() {
                    @Override
                    public void onResponse(Call<CartAddModel> call, Response<CartAddModel> response) {
                        Log.d(TAG, "retrofit  " + response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<CartAddModel> call, Throwable t) {
                        Log.e(TAG, "retrofit error " + t.getMessage());

                    }
                });

            }
        });

        holder.getRecyclerCartitemBinding().minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int preCnt  = Integer.parseInt(holder.getRecyclerCartitemBinding().counter.getText().toString());

                if (preCnt >= 1) {
                    int result = preCnt - 1;
                    cartViewModel.update(new CartItemModel(getItem(position).getName(),
                            result, getItem(position).getPrice()*result , getItem(position).getPrice()));

                    Call<CartAddModel> send = NetRetrofit.getInstance().getService().modifyCart(getItem(position).getName(),
                            getItem(position).getPrice(), "tester", "false");
                    send.enqueue(new Callback<CartAddModel>() {
                        @Override
                        public void onResponse(Call<CartAddModel> call, Response<CartAddModel> response) {
                            Log.d(TAG, "retrofit  " + response.message());
                        }

                        @Override
                        public void onFailure(Call<CartAddModel> call, Throwable t) {
                            Log.e(TAG, "retrofit error " + t.getMessage());

                        }
                    });
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        super.getItemId(position);
        return getItem(position).getName().hashCode();
    }

    public static final DiffUtil.ItemCallback<CartItemModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CartItemModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull CartItemModel oldModel, @NonNull CartItemModel newModel) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return (oldModel.getName().equals(newModel.getName()));
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull CartItemModel oldModel, @NonNull CartItemModel newModel) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldModel.equals(newModel);
                }
            };

    static class BindingHolder extends RecyclerView.ViewHolder {

        private RecyclerCartitemBinding recyclerCartitemBinding;

        BindingHolder(View itemView) {
            super(itemView);
            recyclerCartitemBinding = DataBindingUtil.bind(itemView);
        }

        public RecyclerCartitemBinding getRecyclerCartitemBinding() {
            return recyclerCartitemBinding;
        }
    }
}

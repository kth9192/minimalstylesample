package com.noname.minimalstylesample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.minimalstylesample.databinding.RecyclerPriceitemBinding;
import com.noname.minimalstylesample.model.ItemRoom;
import com.noname.minimalstylesample.observer.PriceObserver;
import com.noname.minimalstylesample.viewmodel.ItemViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDetailAdapter extends ListAdapter<ItemRoom, ItemDetailAdapter.CustomViewHodler> {

    private static String TAG = ItemDetailAdapter.class.getSimpleName();

    protected ItemDetailAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CustomViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_priceitem, parent, false);
        return new CustomViewHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHodler holder, int position) {

        holder.getRecyclerPriceitemBinding().setViewModel(new PriceObserver(getItem(position)));
        holder.getRecyclerPriceitemBinding().executePendingBindings();
    }

    public static final DiffUtil.ItemCallback<ItemRoom> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ItemRoom>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull ItemRoom oldModel, @NonNull ItemRoom newModel) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return (oldModel.getName().equals(newModel.getName()));
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull ItemRoom oldModel, @NonNull ItemRoom newModel) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldModel.equals(newModel);
                }
            };

    static class CustomViewHodler extends RecyclerView.ViewHolder {

        private RecyclerPriceitemBinding recyclerPriceitemBinding;

        public CustomViewHodler(View itemView) {
            super(itemView);
            recyclerPriceitemBinding = DataBindingUtil.bind(itemView);
        }

        public RecyclerPriceitemBinding getRecyclerPriceitemBinding() {
            return recyclerPriceitemBinding;
        }
    }
}

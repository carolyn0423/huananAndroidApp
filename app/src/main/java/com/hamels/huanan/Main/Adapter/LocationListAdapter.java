package com.hamels.huanan.Main.Adapter;

import android.Manifest;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.huanan.Base.BaseAdapter;
import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Main.Contract.LocationListContract;
import com.hamels.huanan.Main.Holder.LocationListHolder;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Store;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends BaseAdapter<LocationListHolder> {
    public static final String TAG = LocationListAdapter.class.getSimpleName();
    private LocationListContract.View view;
    private LocationListContract.Presenter presenter;
    private List<Store> stores;

    public LocationListAdapter(LocationListContract.View view, LocationListContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        stores = new ArrayList<>();
    }

    @NonNull
    @Override
    public LocationListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store_list, viewGroup, false);
        return new LocationListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationListHolder storeListHolder, final int position) {
        storeListHolder.setStore(stores.get(position));

        storeListHolder.ivGoProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goProductMainType(stores.get(position).getLocationID());
            }
        });

        storeListHolder.llBrandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goLocationDesc(stores.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public void setData(List<Store> stores) {
        this.stores = stores;
        notifyDataSetChanged();
    }
}

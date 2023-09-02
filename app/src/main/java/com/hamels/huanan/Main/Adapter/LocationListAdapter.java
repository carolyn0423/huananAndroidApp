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

        if(presenter.getUserLogin()) {
            if (stores.get(position).getIsOften().equals("1")) {
                storeListHolder.tv_storefavorite.setImageResource(R.drawable.favorites_1);
            } else {
                storeListHolder.tv_storefavorite.setImageResource(R.drawable.favoritesgr);
            }

            storeListHolder.btn_storemap.setVisibility(View.GONE);
            storeListHolder.btn_storecall.setVisibility(View.GONE);
            storeListHolder.tv_storefavorite.setVisibility(View.VISIBLE);
        }else{
            storeListHolder.tv_storefavorite.setVisibility(View.GONE);
            storeListHolder.btn_storemap.setVisibility(View.GONE);
            storeListHolder.btn_storecall.setVisibility(View.GONE);
        }

        storeListHolder.btn_storemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.intentToGoogleMap(stores.get(position).getAddress());
            }
        });
        storeListHolder.btn_storecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.checkPermission(new BaseContract.ValueCallback<Boolean>() {
                    @Override
                    public void onValueCallback(int task, Boolean type) {
                        if (type) {
                            view.intentToPhoneCall(stores.get(position).getPhone().replace("-", ""));
                        }
                    }
                }, Manifest.permission.CALL_PHONE);
            }
        });
        storeListHolder.tv_storefavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location_id = "";
                String uid = "";

                if (stores.get(position).getIsOften().equals("1")) {
                    uid = stores.get(position).getOften_uid();
                }
                else {
                    location_id = stores.get(position).getLocationID();
                }
                presenter.setStoreOften(location_id, uid);
            }
        });

        storeListHolder.clItemStoreList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goProductMainType(stores.get(position).getLocationID());
            }
        });

        storeListHolder.ivLocationPicturl.setOnClickListener(new View.OnClickListener() {
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

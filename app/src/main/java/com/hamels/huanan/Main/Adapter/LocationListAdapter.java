package com.hamels.huanan.Main.Adapter;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.huanan.Base.BaseAdapter;
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

    private List<Store> DataLeft = new ArrayList<>();
    private List<Store> DataRight = new ArrayList<>();

    public LocationListAdapter(LocationListContract.View view, LocationListContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }
    public void setData(List<Store> DataLeft, List<Store> DataRight) {
        this.DataLeft = DataLeft;
        this.DataRight = DataRight;

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LocationListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store_list, viewGroup, false);
        return new LocationListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationListHolder storeHolder, final int position) {
        if(DataLeft.size() == DataRight.size()){
            storeHolder.set_two(DataLeft.get(position) , DataRight.get(position));
        } else{
            if(DataRight.size() == position){
                storeHolder.set_one(DataLeft.get(position));
            }
            else{
                storeHolder.set_two(DataLeft.get(position) , DataRight.get(position));
            }
        }

        storeHolder.tvImgLeft.setOnClickListener(img_OnClick_Evt);
        storeHolder.tvImgRight.setOnClickListener(img_OnClick_Evt);

        storeHolder.clItemStoreList.setOnClickListener(Search_OnClick_Evt);
        storeHolder.clBtnProductLeft.setOnClickListener(btn_OnClick_Evt);
        storeHolder.clBtnProductRight.setOnClickListener(btn_OnClick_Evt);
    }
    @Override
    public int getItemCount() {
        if(DataLeft.size() > 0){
            return DataLeft.size();
        } else {
            return DataLeft.size();
        }
    }
    View.OnClickListener Search_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.item_store_list){
                presenter.eventKeyword();
            }
        }
    };
    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Store tmpStore = new Store();

            if(id == R.id.img_left){
                for(int i = 0; i < DataLeft.size(); i++){
                    if(DataLeft.get(i).getLocationID().equals(v.getTag(v.getId()).toString())){
                        tmpStore = DataLeft.get(i);
                    }
                }
            } else {
                for(int i = 0; i < DataRight.size(); i++){
                    if(DataRight.get(i).getLocationID().equals(v.getTag(v.getId()).toString())){
                        tmpStore = DataRight.get(i);
                    }
                }
            }

            presenter.goLocationDesc(tmpStore);
        }
    };

    View.OnClickListener btn_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_product_left || id == R.id.btn_product_right){
                presenter.goProductMainType(v.getTag(v.getId()).toString());
            }
        }
    };
}

package com.hamels.huanan.Main.Holder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Store;

public class LocationListHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout clItemStoreList;
    public TextView tvName, tvPhone, tvAddress, tvOpenTime, tv_storedistance, btn_storecall, btn_storemap;
    public ImageView tv_storefavorite;

    public LocationListHolder(@NonNull View itemView) {
        super(itemView);
        clItemStoreList = itemView.findViewById(R.id.item_store_list);
        tvName = itemView.findViewById(R.id.tv_storename);
        //ViewUtils.addUnderLine(tvName);
        tvPhone = itemView.findViewById(R.id.tv_phone);
        tvAddress = itemView.findViewById(R.id.tv_address);
        tvOpenTime = itemView.findViewById(R.id.tv_time);

        tv_storedistance = itemView.findViewById(R.id.tv_storedistance);
        btn_storecall = itemView.findViewById(R.id.btn_storecall);
        btn_storemap = itemView.findViewById(R.id.btn_storemap);
        tv_storefavorite = itemView.findViewById(R.id.tv_storefavorite);
    }

    public void setStore(Store store) {
        tvName.setText(store.getName());
        tvPhone.setText(store.getPhone());
        tvAddress.setText(store.getAddress());
        tvOpenTime.setText(store.getOpening());

        String tmpdistance = "";
//        if (store.getDistance() > 0) {
//            DecimalFormat decimalFormat = new DecimalFormat(".0"); //取小數點後面兩位
//            Double DoubleTmp = store.getDistance() / 1000; //Double取小數點後面兩位
//            String StringTmp = "";//最終取到的結果
//            StringTmp = decimalFormat.format(DoubleTmp);
//            tmpdistance = StringTmp + "km";
//        }
        tv_storedistance.setText(tmpdistance);

//        if(presenter.getUserLogin()) {
//            if (store.getIsOften().equals("1")) {
//                tv_storefavorite.setImageResource(R.drawable.favorites_1);
//            } else {
//                tv_storefavorite.setImageResource(R.drawable.favoritesgr);
//            }
//        }else{
//            tv_storefavorite.setVisibility(View.GONE);
//        }
    }
}

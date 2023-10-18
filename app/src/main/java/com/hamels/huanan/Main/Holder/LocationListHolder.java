package com.hamels.huanan.Main.Holder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.View.LocationFragment;
import com.hamels.huanan.Product.Holder.ProductMainTypeHolder;
import com.hamels.huanan.Product.View.ProductMainTypeFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.ProductMainType;
import com.hamels.huanan.Repository.Model.Store;

public class LocationListHolder extends RecyclerView.ViewHolder {
    public static final String TAG = LocationListHolder.class.getSimpleName();

    public TextView tvNameLeft, tvNameRight;
    public ImageView tvImgLeft, tvImgRight;
    public ConstraintLayout clConstraintLayoutLeft, clConstraintLayoutRight;
    public ConstraintLayout clItemStoreList, clBtnProductLeft, clBtnProductRight;
    public View vItemLocationLeft, vItemLocationRight;

    public LocationListHolder(@NonNull View itemView) {
        super(itemView);
        clConstraintLayoutLeft = itemView.findViewById(R.id.constraintLayout_left);
        clConstraintLayoutRight = itemView.findViewById(R.id.constraintLayout_right);

        tvNameLeft = itemView.findViewById(R.id.tv_name_left);
        tvNameRight = itemView.findViewById(R.id.tv_name_right);

        tvImgLeft = itemView.findViewById(R.id.img_left);
        tvImgRight = itemView.findViewById(R.id.img_right);

        clItemStoreList = itemView.findViewById(R.id.item_store_list);
        clBtnProductLeft = itemView.findViewById(R.id.btn_product_left);
        clBtnProductRight = itemView.findViewById(R.id.btn_product_right);

        vItemLocationLeft = itemView.findViewById(R.id.item_location_left);
        vItemLocationRight = itemView.findViewById(R.id.item_location_right);
    }

    public void set_one(Store store) {
        clConstraintLayoutLeft.setVisibility(View.VISIBLE);
        clConstraintLayoutRight.setVisibility(View.GONE);

        tvImgLeft.setVisibility(View.VISIBLE);
        tvImgRight.setVisibility(View.GONE);

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));

        String sPictureUrl = store.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : store.getPictureUrl();
        Glide.with(LocationFragment.getInstance("")).load(EOrderApplication.sApiUrl + sPictureUrl).apply(requestOptions_left).into(tvImgLeft);

        vItemLocationLeft.setTag(R.id.item_location_left, store.getLocationID());
        tvImgLeft.setTag(R.id.img_left, store.getLocationID());
        clBtnProductLeft.setTag(R.id.btn_product_left, store.getLocationID());

        tvNameLeft.setText(store.getName());
        tvNameRight.setText("");
    }

    public void set_two(Store store_left , Store store_right) {
        clConstraintLayoutLeft.setVisibility(View.VISIBLE);
        clConstraintLayoutRight.setVisibility(View.VISIBLE);

        tvImgLeft.setVisibility(View.VISIBLE);
        tvImgRight.setVisibility(View.VISIBLE);

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));
        RequestOptions requestOptions_right = new RequestOptions()
                .transform(new RoundedCorners(20));

        String sLeftPictureUrl = store_left.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : store_left.getPictureUrl();
        String sRightPictureUrl = store_right.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : store_right.getPictureUrl();

        Glide.with(LocationFragment.getInstance("")).load(EOrderApplication.sApiUrl + sLeftPictureUrl).apply(requestOptions_left).into(tvImgLeft);
        Glide.with(LocationFragment.getInstance("")).load(EOrderApplication.sApiUrl + sRightPictureUrl).apply(requestOptions_right).into(tvImgRight);

        vItemLocationLeft.setTag(R.id.item_location_left, store_left.getLocationID());
        vItemLocationRight.setTag(R.id.item_location_right, store_right.getLocationID());

        tvImgLeft.setTag(R.id.img_left, store_left.getLocationID());
        tvImgRight.setTag(R.id.img_right, store_right.getLocationID());

        clBtnProductLeft.setTag(R.id.btn_product_left, store_left.getLocationID());
        clBtnProductRight.setTag(R.id.btn_product_right, store_right.getLocationID());

        tvNameLeft.setText(store_left.getName());
        tvNameRight.setText(store_right.getName());
    }
}

package com.hamels.huanan.Main.Holder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.View.LocationFragment;
import com.hamels.huanan.Product.View.ProductMainTypeFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Store;

public class LocationListHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout clItemStoreList;
    public TextView tvName, tvBrandDescription;
    public ImageView ivGoProduct, ivLocationPicturl;
    public LinearLayout llBrandLayout;

    public LocationListHolder(@NonNull View itemView) {
        super(itemView);
        clItemStoreList = itemView.findViewById(R.id.item_store_list);
        tvName = itemView.findViewById(R.id.tv_storename);
        llBrandLayout = itemView.findViewById(R.id.brand_layout);
        ivGoProduct = itemView.findViewById(R.id.go_product);
        ivLocationPicturl = itemView.findViewById(R.id.location_picturl);
        tvBrandDescription = itemView.findViewById(R.id.brand_description);
    }

    public void setStore(Store store) {
        tvName.setText(store.getName());

        // 应用圆角转换的 RequestOptions
        RequestOptions requestOptions_left = new RequestOptions()
                .transform(new RoundedCorners(20));
        String sPictureUrl = store.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : store.getPictureUrl();
        Glide.with(LocationFragment.getInstance()).load(EOrderApplication.sApiUrl + sPictureUrl).apply(requestOptions_left).into(ivLocationPicturl);
    }
}

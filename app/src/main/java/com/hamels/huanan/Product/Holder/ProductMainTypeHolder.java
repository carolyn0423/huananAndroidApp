package com.hamels.huanan.Product.Holder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.huanan.Product.View.ProductMainTypeFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.ProductMainType;

public class ProductMainTypeHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductMainTypeHolder.class.getSimpleName();

    public ImageView img_productMainType_left , img_productMainType_right;
    public ImageView img_merchant_left , img_merchant_right;
    public TextView tv_productMainType_name_left, tv_productMainType_name_right;

    public ConstraintLayout constraintLayout_right, constraintLayout_left;

    public ProductMainTypeHolder(@NonNull View itemView) {
        super(itemView);

        img_productMainType_right = itemView.findViewById(R.id.img_productMainType_right);
        img_productMainType_left = itemView.findViewById(R.id.img_productMainType_left);

        tv_productMainType_name_left = itemView.findViewById(R.id.tv_productMainType_name_left);
        tv_productMainType_name_right = itemView.findViewById(R.id.tv_productMainType_name_right);

        img_merchant_right = itemView.findViewById(R.id.img_merchant_right);
        img_merchant_left = itemView.findViewById(R.id.img_merchant_left);

        constraintLayout_right = itemView.findViewById(R.id.constraintLayout_right);
        constraintLayout_left = itemView.findViewById(R.id.constraintLayout_left);


    }
    public void setImg_ProductMainType_one(ProductMainType mainTypeleft, String sImageUrl, String sETicket) {
        Log.e(TAG,mainTypeleft.getPicture_url());
        img_productMainType_left.setVisibility(View.VISIBLE);
        img_productMainType_right.setVisibility(View.VISIBLE);

        Glide.with(ProductMainTypeFragment.getInstance()).load(sImageUrl + mainTypeleft.getPicture_url()).into(img_productMainType_left);
        img_productMainType_left.setTag(R.id.img_productMainType_left,mainTypeleft.getId());
        constraintLayout_left.setTag(R.id.constraintLayout_left,mainTypeleft.getId());

        tv_productMainType_name_left.setText(mainTypeleft.getProduct_Type_Main_Name());
        tv_productMainType_name_right.setText("");
    }

    public void setImg_ProductMainType_two(ProductMainType mainTypeleft , ProductMainType mainTyperight, String sImageUrl, String sETicket) {
        img_productMainType_left.setVisibility(View.VISIBLE);
        img_productMainType_right.setVisibility(View.VISIBLE);

        Glide.with(ProductMainTypeFragment.getInstance()).load(sImageUrl + mainTypeleft.getPicture_url()).into(img_productMainType_left);
        Glide.with(ProductMainTypeFragment.getInstance()).load(sImageUrl + mainTyperight.getPicture_url()).into(img_productMainType_right);

        img_productMainType_left.setTag(R.id.img_productMainType_left, mainTypeleft.getId());
        img_productMainType_right.setTag(R.id.img_productMainType_right, mainTyperight.getId());

        constraintLayout_left.setTag(R.id.constraintLayout_left,mainTypeleft.getId());
        constraintLayout_right.setTag(R.id.constraintLayout_right,mainTyperight.getId());

        tv_productMainType_name_left.setText(mainTypeleft.getProduct_Type_Main_Name());
        tv_productMainType_name_right.setText(mainTyperight.getProduct_Type_Main_Name());
    }
}
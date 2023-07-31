package com.hamels.huanan.Product.Holder;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.huanan.Product.View.ProductFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Product;
import com.hamels.huanan.EOrderApplication;
import  com.hamels.huanan.Utils.WaterMaskUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductHolder.class.getSimpleName();

    public ImageView img_product;
    public TextView tv_product_name, tv_price, tv_sale_price,tv_water_mask;
    public ConstraintLayout layout;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        img_product = itemView.findViewById(R.id.img_product);
        tv_water_mask = itemView.findViewById(R.id.tv_water_mask);
        tv_product_name = itemView.findViewById(R.id.tv_product_name);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
        layout = itemView.findViewById(R.id.layout_constraint);
    }

    public void setImg_product(Product product) {
        Glide.with(ProductFragment.getInstance()).load(EOrderApplication.DOMAIN + product.getPicture_url()).into(img_product);
        if (product.getSoldoutToday().equals("Y")){
            List<String> labels = new ArrayList<>();
            labels.add("今日完售");
            tv_water_mask.setBackground(new WaterMaskUtils(ProductFragment.getInstance().getContext(),labels,-30,13));
        }

        img_product.setTag(R.id.img_product, product.getId());
        layout.setTag(R.id.layout_constraint, product.getId());
        tv_product_name.setText(product.getProduct_name());

        if (1 < product.getspec_cnt()) {
            tv_sale_price.setText("NT$" + mDecimalFormat.format((double) product.getSale_price()) + " 起");

            if (product.getSale_price() != product.getPrice()) {
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText("NT$" + mDecimalFormat.format((double) product.getPrice()));
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                tv_price.setPadding(0, 50, 0, 0);
                tv_sale_price.setPadding(0, 50, 0, 0);
            } else {
                tv_price.setVisibility(View.GONE);
            }
        } else {
            tv_sale_price.setText("NT$" + mDecimalFormat.format((double) product.getSale_price()));

            if (product.getSale_price() != product.getPrice()) {
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText("NT$" + mDecimalFormat.format((double) product.getPrice()));
                tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                tv_price.setPadding(0, 50, 0, 0);
                tv_sale_price.setPadding(0, 50, 0, 0);
            } else {
                tv_price.setVisibility(View.GONE);
            }
        }
    }


}
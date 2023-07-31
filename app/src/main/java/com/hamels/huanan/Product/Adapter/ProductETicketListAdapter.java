package com.hamels.huanan.Product.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hamels.huanan.Base.BaseAdapter;
import com.hamels.huanan.Product.Contract.ProductContract;
import com.hamels.huanan.Product.Holder.ProductETicketHolder;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductETicketListAdapter extends BaseAdapter<ProductETicketHolder> {
    public static final String TAG = ProductETicketListAdapter.class.getSimpleName();
    private ProductContract.Presenter presenter;

    private List<Product> productList = new ArrayList<>();

    public ProductETicketListAdapter(ProductContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProductETicketHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_new_ticket, viewGroup, false);
        return new ProductETicketHolder(viewProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductETicketHolder productETicketHolder, final int position) {
        Log.e(TAG,"onBindViewHolder");
        productETicketHolder.setImg_product(productList.get(position));
        productETicketHolder.img_product.setOnClickListener(img_OnClick_Evt);
        productETicketHolder.layout.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void setProduct(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.img_product || id == R.id.layout_constraint){
                presenter.getProductDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
            }
        }
    };
}
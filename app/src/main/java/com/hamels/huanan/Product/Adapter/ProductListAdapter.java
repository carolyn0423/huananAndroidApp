package com.hamels.huanan.Product.Adapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.huanan.Base.BaseAdapter;
import com.hamels.huanan.Product.Contract.ProductContract;
import com.hamels.huanan.Product.Holder.ProductHolder;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends BaseAdapter<ProductHolder> {
    public static final String TAG = ProductListAdapter.class.getSimpleName();
    private ProductContract.Presenter presenter;

    private List<Product> productList = new ArrayList<>();

    public ProductListAdapter(ProductContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_new, viewGroup, false);
        return new ProductHolder(viewProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder productHolder, final int position) {
        Log.e(TAG,"onBindViewHolder");
        productHolder.setImg_product(productList.get(position));
        productHolder.img_product.setOnClickListener(img_OnClick_Evt);
        productHolder.layout.setOnClickListener(img_OnClick_Evt);
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

    public static Bitmap mark(Bitmap src, String watermark, Point location, Color color, int alpha, int size, boolean underline) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color.parseColor("#709868"));
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }
}
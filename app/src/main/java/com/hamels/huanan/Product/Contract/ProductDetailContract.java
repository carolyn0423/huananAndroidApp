package com.hamels.huanan.Product.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Product;

import java.util.List;

public interface ProductDetailContract {
    interface View extends BaseContract.View {
        void setProductDetail(List<Product> productDetail);

        void intentToLogin(int requestCode);

        void showErrorAlert(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProductDetailByID(String product_ID, String e_ticket);

        void addShoppingCart(String product_id, String spec_id, String location_id, String spec_qty, String stock, String quantity, String order_type, String conf_list, int conf_quantity);
    }
}
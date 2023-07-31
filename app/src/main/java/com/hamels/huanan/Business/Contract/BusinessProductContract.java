package com.hamels.huanan.Business.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Product;
import com.hamels.huanan.Repository.Model.ProductType;

import java.util.List;

public interface BusinessProductContract {
    interface View extends BaseContract.View {
        void setProductTypeList(List<ProductType> productTypeList);

        void goPageProductDetail(int product_id);

        void setProductList(List<Product> productList);


    }

    interface Presenter extends BaseContract.Presenter {
        void getTypeList(int store_id);

        void getProductDetailByID(int product_ID);

        void getProductList(String location_id,String sort, String prodduct_type_main_id, String product_type_id, String product_name , String business_sale_id, String e_ticket);
    }
}

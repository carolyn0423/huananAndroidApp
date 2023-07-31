package com.hamels.huanan.Product.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.ProductMainType;

import java.util.List;

public interface ProductMainTypeContract {
    interface View extends BaseContract.View {
        void setProductMainTypeList(List<ProductMainType> productMainTypeList);

        void getProductList(int product_type_main_id);

        void setOnlineShoppingFlag(boolean isFlag);
    }

    interface Presenter extends BaseContract.Presenter {
        void getProductMainTypeList(String location_id);

        void getProductList(int product_type_main_id);

        String getApiUrl();

        boolean getUserLogin();

        String getFragmentMainType(String sPram);
    }
}

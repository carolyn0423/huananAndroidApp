package com.hamels.huanan.Business.Contract;
import com.hamels.huanan.Base.BaseContract;

public interface BusinessContract {
    interface View extends BaseContract.View {
        void goBusinessProduct(String business_sale_id);

        void ErrorAlert(String errorMessage);
    }

    interface Presenter extends BaseContract.Presenter {
        void checkBusinessCode(String sale_password);
    }
}

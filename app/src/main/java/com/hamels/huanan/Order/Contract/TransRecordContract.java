package com.hamels.huanan.Order.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Order;

import java.util.List;

public interface TransRecordContract {
    interface View extends BaseContract.View {
        void addReturnFragment(Order order, int status);

        void setStatusPage(int page);

        void onOrderListReadyCallback();
    }

    interface Presenter extends BaseContract.Presenter {
        void getOrders();

        List<Order> getStatusOrders(int status);
    }
}

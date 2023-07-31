package com.hamels.huanan.Order.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Order;

import java.util.List;

public interface OrderListContract {
    interface View extends BaseContract.View {
        void setList(List<Order> orders);
    }

    interface Presenter extends BaseContract.Presenter {

    }
}

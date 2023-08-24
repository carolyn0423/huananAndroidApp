package com.hamels.huanan.MemberCenter.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.PointHistory;
import com.hamels.huanan.Repository.Model.User;

import java.util.List;

public interface MemberPointContract {
    interface View extends BaseContract.View {
        void setPoint(List<PointHistory> point);

        void setPointData(User user);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberPoint(String date);

        void getPointData();
    }
}

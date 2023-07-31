package com.hamels.huanan.MemberCenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.About;

import java.util.List;

public interface AboutContract {
    interface View extends BaseContract.View {
        void setAboutData(List<About> aboutList);

        void intentToPhoneCall(String phone);
    }

    interface Presenter extends BaseContract.Presenter {
        void getAboutData();

        void callPhone(String phone);
    }
}

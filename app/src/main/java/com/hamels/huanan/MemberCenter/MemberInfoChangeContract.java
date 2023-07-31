package com.hamels.huanan.MemberCenter;

import android.support.annotation.StringRes;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Address;
import com.hamels.huanan.Repository.Model.User;

import java.util.List;

public interface MemberInfoChangeContract {
    interface View extends BaseContract.View {
        void setUser(User user);

        void setPropertyCode(List<Address> addressList);

        void setBirthDayModifiedOrNot(String message);

        void showAlert(@StringRes int messageId);

        void showAlert(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberInfo();

        void getPropertyData();

        void updateMember(String customerId, String city_code, String area_code, String address, String email, String birth);
    }
}

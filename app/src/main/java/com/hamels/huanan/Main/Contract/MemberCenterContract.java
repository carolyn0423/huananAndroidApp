package com.hamels.huanan.Main.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.User;

public interface MemberCenterContract {
    interface View extends BaseContract.View {
        void setUserName(String name);

        void setUserPoint(String point);

        void setUserMobile(String mobile);

//        void setUserQrCode(String point);

        void redirectToMainPage();

        void setPointData(User user);

        void deleteMember();

        void deleteError(String s);
    }

    interface Presenter extends BaseContract.Presenter {
        void getUserInfo();

        void logout();

        void getPointData();

        void getDeleteMember();

        boolean getUserLogin();

        void saveSourceActive(String sSourceActive);

        Object getShopkeeper();

        String getUserID();

        String getMobile();
    }
}

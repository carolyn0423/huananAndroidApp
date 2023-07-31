package com.hamels.huanan.Main.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.User;

public interface MemberCardContract {
    interface View extends BaseContract.View {
        void setUerInfo(User user);

        void setMemberCardInfo(String cardNum);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMemberInfo();
    }
}

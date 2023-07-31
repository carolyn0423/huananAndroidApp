package com.hamels.huanan.Main.Contract;

import com.hamels.huanan.Base.BaseContract;

public interface WelcomeContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {
        Boolean getUserInfo();

        String getSourceActive();
    }
}

package com.hamels.huanan.Login.Contract;

import com.hamels.huanan.Base.BaseContract;

public interface VerifyCodeContract {
    interface View extends BaseContract.View {
        void showResendHint(String hint);

        void showWrongVerifyHint();

        void showWrongVerifyHint(boolean isSuccess ,String hint);

        void showWrongVerifyHint(String hint);

        void finishActivity();
    }

    interface Presenter extends BaseContract.Presenter {
        void resendSms();

        void goLogin();

        void checkVerifyCodeAndFinish(String code);

        void logout();
    }
}

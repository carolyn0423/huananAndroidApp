package com.hamels.huanan.Login.Contract;

import com.hamels.huanan.Base.BaseContract;

public interface ForgetPasswordContract {
    interface View extends BaseContract.View {
        void showAlert(String message);

        void showEmptyVerifyCodeAlert();

        void showPasswordFormatAlert();

        void showPasswordCheckFormatAlert();

        void showPasswordDifferentAlert();

        void showGoRegistAlert(String message);

        void intentToRegister();

        void finishActivity();
    }

    interface Presenter extends BaseContract.Presenter {
        void register();

        void checkInputValue(String cellphone, String verifyCode, String password, String passwordCheck);
    }
}

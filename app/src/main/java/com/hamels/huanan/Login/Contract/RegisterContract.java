package com.hamels.huanan.Login.Contract;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import com.hamels.huanan.Base.BaseContract;

public interface RegisterContract {
    interface View extends BaseContract.View {
        void showErrorMessage(@StringRes int stringRes);

        void showErrorMessage(String message);

        void intentToTermsOfUse();

        void intentToVerifyCode();
    }

    interface Presenter extends BaseContract.Presenter {
        void checkInputValue(String name, @IdRes int selectRadioId, String birth, String phone, String password, String repassword, String InvitationCode, boolean isAgreeTermsOfUse);
    }
}

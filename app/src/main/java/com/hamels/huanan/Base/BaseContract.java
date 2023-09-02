package com.hamels.huanan.Base;

import android.content.Context;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import com.hamels.huanan.Repository.RepositoryManager;

public interface BaseContract {
    interface View {
        RepositoryManager getRepositoryManager(Context context);

        void setAppToolbar(@IdRes int appToolbarId);

        void setAppTitle(@StringRes int resString);

        void setBottomNavigation(@IdRes int BottomNavigationID);

        void setCartBadge(@IdRes int CartBadgeID);

        void setAppBadge();

        void setAppToolbarVisibility(boolean isVisible);

        void setBottomNavigationVisibility(boolean isVisible);

        void setCartBadgeVisibility(boolean isVisible);

        void setBackButtonVisibility(boolean isVisible);

        void setMailButtonVisibility(boolean isVisible);

        void setMessageButtonVisibility(boolean isVisible);

        void setSortButtonVisibility(boolean isVisible);

        void showToast(String message);

        void checkPermission(ValueCallback<Boolean> callback, String... permission);

        void showLoadingDialog();

        void hideLoadingDialog();
    }

    interface Presenter {
        boolean isLogin();

        void forceLogout();
    }

    interface ValueCallback<T> {
        void onValueCallback(int task, T type);
    }
}

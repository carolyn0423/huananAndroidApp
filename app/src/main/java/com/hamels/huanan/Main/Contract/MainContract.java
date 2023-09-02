package com.hamels.huanan.Main.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Repository.Model.Customer;
import com.hamels.huanan.Repository.Model.User;

public interface MainContract {
    interface View extends BaseContract.View {
        void refreshBadge();

        void setMailBadge(String count);

        void setMessageBadge(String count);

        void setAllBadge(String count);

        void setShoppingCartCount(String Count);

        void changeTabFragment(BaseFragment willChangeFragment);

        void addFragment(BaseFragment baseFragment);

        void intentToLogin(int requestCode);

        void setCustomer(Customer customers, String sCustomerID, String sCustomerName, String sApiUrl);

        void getVersion(String sOnlineVision);

        void ProductLocationFragment(int size);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMailBadgeFromApi();

        void getMessageBadgeFromApi();

        void getBadgeNumberFromApi();

        void checkLoginForMemberCard();

        void checkLoginForCoupon();

        void checkLoginForLot();

        void checkLoginForBusiness();

        void goNewsDetail(String news_id);

        User JsCallGetMemberInfo();

        void checkLoginForMemberCenter();

        void checkLoginForMessage();

        void checkLoginForMessageList();

        void checkLoginForShoppingCart(String orderType);

        void checkLoginForMail(String fragmentName);

        void checkLoginForMessage(String fragmentName);

        void checkLoginForDonate();

//        void checkLoginForIndex();

        Boolean getUserLogin();

        void logout();

        void setCustomerData();

        void checkCustomerNo(String sCutomerNo);

        void getCustomer();

        void getOnlineVision();

        String getSourceActive();

        void saveSourceActive(String sSourceActive);

        void saveFragmentMainType(String sLocationID, String IsETicket);

        void saveFragmentLocation(String sLocationID);

        String getCustomerID();

        String getApiUrl();

        void getLocationList();
    }
}

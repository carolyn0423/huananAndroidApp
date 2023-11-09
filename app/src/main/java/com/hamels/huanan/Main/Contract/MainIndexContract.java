package com.hamels.huanan.Main.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Carousel;

import java.util.List;

public interface MainIndexContract {
    interface View extends BaseContract.View {
        void setCarouselList(List<Carousel> carouselList);

        void setMemberCardImg(String group);

        void CustomerOnlineISFalse();

        void getNoVerift();
    }

    interface Presenter extends BaseContract.Presenter {
        void getCarouselList(String sCustomer_id);

        String getName();

        String getGroup();

        void checkMemberData();

        boolean getUserLogin();

        void logout();
    }
}

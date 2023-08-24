package com.hamels.huanan.MemberCenter.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Faq;

public interface FaqContract {
    interface View extends BaseContract.View {
        void setFaqData(Faq faq);
    }

    interface Presenter extends BaseContract.Presenter {
        void getFaqData(String faq_id);
    }
}

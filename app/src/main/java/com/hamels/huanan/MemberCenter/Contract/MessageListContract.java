package com.hamels.huanan.MemberCenter.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Message;

import java.util.List;

public interface MessageListContract {
    interface View extends BaseContract.View {
        void setMessageList(List<Message> list);

        void queryCustomerServiceAPI();
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList(String sMemberID);

        void updateReadMessageApi();

        void sendMessage(String sMemberID, String message);

        void reSendMessage(String sMemberID, String message);

        boolean getUserLogin();

        String getUserID();
    }
}
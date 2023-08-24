package com.hamels.huanan.MemberCenter.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Message;

import java.util.List;

public interface MessageListContract {
    interface View extends BaseContract.View {
        void setMessageList(List<Message> list);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        void updateReadMessageApi();

        void sendMessage(String message);
    }
}

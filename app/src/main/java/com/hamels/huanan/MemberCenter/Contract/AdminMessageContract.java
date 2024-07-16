package com.hamels.huanan.MemberCenter.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.MessageGroup;

import java.util.List;

public interface AdminMessageContract {
    interface View extends BaseContract.View {
        void setMessageList(List<MessageGroup> messageGroups);

        void goMessageList(String sReMemberID, String sMobile);
    }

    interface Presenter extends BaseContract.Presenter {
        void getMessageList();

        boolean getUserLogin();

        void goMessageList(MessageGroup messageGroup);
    }
}

package com.hamels.huanan.MemberCenter.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.MemberCenter.Contract.MailFileContract;
import com.hamels.huanan.Repository.Model.MemberMessage;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;

public class MailFilePresenter extends BasePresenter<MailFileContract.View> implements MailFileContract.Presenter {
    public static final String TAG = MailFilePresenter.class.getSimpleName();

    public MailFilePresenter(MailFileContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMessageList() {
        repositoryManager.callMemberMessageListApi(new BaseContract.ValueCallback<List<MemberMessage>>() {
            @Override
            public void onValueCallback(int task, List<MemberMessage> type) {
                view.setMessageList(type);
            }
        });
    }

    @Override
    public void deleteMessage(MemberMessage memberMessage, BaseContract.ValueCallback<String> valueCallback) {
        repositoryManager.callUpdateMessageStatusApi(memberMessage.getId(), MemberMessage.STATUS_DELETE, valueCallback);
    }

    @Override
    public void readMessage(final MemberMessage memberMessage) {
        repositoryManager.callUpdateMessageStatusApi(memberMessage.getId(), MemberMessage.STATUS_READ, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.showMailDetail(memberMessage);
            }
        });
    }
    public boolean getUserLogin(){return repositoryManager.getUserLogin();}
}

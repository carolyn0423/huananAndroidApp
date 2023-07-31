package com.hamels.huanan.MemberCenter.Presenter;

import android.util.Log;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.MemberCenter.MessageListContract;
import com.hamels.huanan.Repository.Model.Message;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;

public class MessageListPresenter extends BasePresenter<MessageListContract.View> implements MessageListContract.Presenter {
    public static final String TAG = MessageListPresenter.class.getSimpleName();

    public MessageListPresenter(MessageListContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMessageList() {
        repositoryManager.callGetMessageListApi(new BaseContract.ValueCallback<List<Message>>() {
            @Override
            public void onValueCallback(int task, List<Message> type) {
                Log.e(TAG,type.toString());
                view.setMessageList(type);
            }
        });
    }

    @Override
    public void updateReadMessageApi() {
        repositoryManager.callReadMessageApi(new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        repositoryManager.callSendMessageApi(message, new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                getMessageList();
            }
        });
    }
}

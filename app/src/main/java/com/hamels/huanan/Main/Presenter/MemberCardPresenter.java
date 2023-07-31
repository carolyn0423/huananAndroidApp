package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Main.Contract.MemberCardContract;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.Repository.RepositoryManager;

public class MemberCardPresenter extends BasePresenter<MemberCardContract.View> implements MemberCardContract.Presenter {
    public static final String TAG = MemberCardPresenter.class.getSimpleName();

    public MemberCardPresenter(MemberCardContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMemberInfo() {
        repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.setUerInfo(user);
//                view.setMemberCardInfo(user.getMembershipCode(), user.getMembershipSrc());
                view.setMemberCardInfo(user.getMembershipCode());
            }
        });
    }
}

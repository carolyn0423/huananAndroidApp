package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Main.Contract.MemberCenterContract;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.Repository.RepositoryManager;

public class MemberCenterPresenter extends BasePresenter<MemberCenterContract.View> implements MemberCenterContract.Presenter {
    public static final String TAG = MemberCenterPresenter.class.getSimpleName();

    public MemberCenterPresenter(MemberCenterContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getUserInfo() {
        User user = repositoryManager.getUser();
        if (user != null) {
            view.setUserName(user.getName());
            view.setUserPoint(user.getPoint());
            view.setUserMobile(user.getMobile());
//            view.setUserQrCode(user.getMembershipCode());
        }
    }

    @Override
    public void logout() {
        repositoryManager.callLogOutApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.redirectToMainPage();
            }
        });
    }

    @Override
    public void getPointData() {
        repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<User>() {
            @Override
            public void onValueCallback(int task, User user) {
                view.setPointData(user);
            }
        });
    }
    @Override
    public void getDeleteMember() {
        repositoryManager.callDeleteMemberApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String user) {
                view.deleteMember();
            }
        });
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin(); }
    public void saveSourceActive(String sSourceActive) { repositoryManager.saveSourceActive(sSourceActive); }
}

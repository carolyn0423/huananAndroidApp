package com.hamels.huanan.MemberCenter.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.MemberCenter.Contract.MemberPointContract;
import com.hamels.huanan.Repository.Model.PointHistory;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;

public class MemberPointPresenter extends BasePresenter<MemberPointContract.View> implements MemberPointContract.Presenter {
    public static final String TAG = MemberPointPresenter.class.getSimpleName();

    public MemberPointPresenter(MemberPointContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getMemberPoint(String date) {
        repositoryManager.callMemberPointHistoryApi(date ,new BaseContract.ValueCallback<List<PointHistory>>() {
            @Override
            public void onValueCallback(int task, List<PointHistory> pointHistory) {
                view.setPoint(pointHistory);
//                view.setPointLogList(memberPoint.getInTimeLog());
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
}

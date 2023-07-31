package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Main.Contract.WelcomeContract;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.Repository.RepositoryManager;


public class WelcomePresenter extends BasePresenter<WelcomeContract.View> implements WelcomeContract.Presenter {
    public static final String TAG = WelcomePresenter.class.getSimpleName();

    public WelcomePresenter(WelcomeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public Boolean getUserInfo() {
        User user = repositoryManager.getUser();
        if (user != null) {
            return true;
        }else{
            return false;
        }
    }

    public String getSourceActive() { return repositoryManager.getSourceActive(); }
}

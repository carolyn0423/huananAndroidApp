package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Main.Contract.NewsContract;
import com.hamels.huanan.Repository.RepositoryManager;


public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {
    public static final String TAG = NewsPresenter.class.getSimpleName();

    public NewsPresenter(NewsContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }
}

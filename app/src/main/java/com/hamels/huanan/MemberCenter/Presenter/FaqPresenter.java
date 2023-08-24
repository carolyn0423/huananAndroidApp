package com.hamels.huanan.MemberCenter.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.MemberCenter.Contract.FaqContract;
import com.hamels.huanan.Repository.Model.Faq;
import com.hamels.huanan.Repository.RepositoryManager;

public class FaqPresenter extends BasePresenter<FaqContract.View> implements FaqContract.Presenter {
    public static final String TAG = FaqPresenter.class.getSimpleName();

    public FaqPresenter(FaqContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getFaqData(String faq_id) {
        repositoryManager.callFaqDataApi(faq_id , new BaseContract.ValueCallback<Faq>() {
            @Override
            public void onValueCallback(int task, Faq faq) {
                view.setFaqData(faq);
            }
        });
    }
}

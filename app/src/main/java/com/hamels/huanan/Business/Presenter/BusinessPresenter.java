package com.hamels.huanan.Business.Presenter;
import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Business.Contract.BusinessContract;
import com.hamels.huanan.Repository.RepositoryManager;
import static com.hamels.huanan.Constant.ApiConstant.TASK_POST_GET_BUSINESS_CODE;

public class BusinessPresenter extends BasePresenter<BusinessContract.View> implements BusinessContract.Presenter {
    public static final String TAG = BusinessPresenter.class.getSimpleName();

    public BusinessPresenter(BusinessContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkBusinessCode(String sale_password) {
        repositoryManager.callGetBusinessCodeApi(sale_password ,  new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                if(task == TASK_POST_GET_BUSINESS_CODE){
                    repositoryManager.saveBusinessSaleID(type);
                    view.goBusinessProduct(type);
                }
                else{
                    view.ErrorAlert(type);
                }
            }
        });
    }
}

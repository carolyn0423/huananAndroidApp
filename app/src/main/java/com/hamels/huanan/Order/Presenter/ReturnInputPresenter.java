package com.hamels.huanan.Order.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Order.Contract.ReturnInputContract;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.OrderProduct;
import com.hamels.huanan.Repository.RepositoryManager;
import com.hamels.huanan.Utils.FormatUtils;

import java.util.ArrayList;

import static com.hamels.huanan.Constant.ApiConstant.TASK_POST_RETURN_ORDER_SUCCESS;

public class ReturnInputPresenter extends BasePresenter<ReturnInputContract.View> implements ReturnInputContract.Presenter {
    public static final String TAG = ReturnInputPresenter.class.getSimpleName();

    public ReturnInputPresenter(ReturnInputContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void checkInputAndSend(ArrayList<OrderProduct> products, int returnType, String name, String address, String phone, String email) {
        if (name.isEmpty()) {
            view.showAlert(R.string.name_empty);
            return;
        }
        if (address.isEmpty()) {
            view.showAlert(R.string.address_empty);
            return;
        }
        if (phone.isEmpty() || !FormatUtils.isCellphoneFormat(phone)) {
            view.showAlert(R.string.phone_format_error);
            return;
        }
        if (email.isEmpty() || !FormatUtils.isEmailFormat(email)) {
            view.showAlert(R.string.email_format_error);
            return;
        }

        callReturnOrdersApi(products, returnType, name, address, phone, email);
    }

    private void callReturnOrdersApi(ArrayList<OrderProduct> products, int returnType, String name, String address, String phone, String email) {
        repositoryManager.callReturnProductsApi(products, name, address, phone, email, returnType, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.showReturnResult(task == TASK_POST_RETURN_ORDER_SUCCESS, type);
            }
        });
    }
}

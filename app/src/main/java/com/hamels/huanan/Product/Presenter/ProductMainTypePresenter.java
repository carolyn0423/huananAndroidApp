package com.hamels.huanan.Product.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Product.Contract.ProductMainTypeContract;
import com.hamels.huanan.Repository.Model.ProductMainType;
import com.hamels.huanan.Repository.Model.Store;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;


public class ProductMainTypePresenter extends BasePresenter<ProductMainTypeContract.View> implements ProductMainTypeContract.Presenter {
    public static final String TAG = ProductMainTypePresenter.class.getSimpleName();

    public ProductMainTypePresenter(ProductMainTypeContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getProductMainTypeList(String location_id) {
        String sCustomerID = repositoryManager.getCustomerID();

        String isETicket = getFragmentMainType("ISETICKET");
        if(isETicket.equals("Y")) {
            //  先取得該商家的門市總店
            repositoryManager.callGetLocationApi("AppLocation", sCustomerID, "", "0", "Y", "", new BaseContract.ValueCallback<List<Store>>() {
                @Override
                public void onValueCallback(int task, List<Store> type) {
                    if(type.get(0).getHeadLocationFlag().equals("Y")){
                        //  總店判斷是否有 線上購物
                        if(type.get(0).getOrderSource().indexOf("1") != -1){
                            callGetProductMainTypeListApi(location_id, sCustomerID);
                        }else{
                            //  無線上購物功能
                            view.setOnlineShoppingFlag(false);
                        }
                    }
                }
            });
        }else{
            callGetProductMainTypeListApi(location_id, sCustomerID);
        }
    }

    public void callGetProductMainTypeListApi(String location_id, String sCustomerID){
        view.setOnlineShoppingFlag(true);

        repositoryManager.callGetProductMainTypeListApi(location_id, sCustomerID, new BaseContract.ValueCallback<List<ProductMainType>>() {
            @Override
            public void onValueCallback(int task, List<ProductMainType> type) {
                view.setProductMainTypeList(type);
            }
        });
    }

    @Override
    public void getProductList(int product_type_main_id) { view.getProductList(product_type_main_id); }

    public boolean getUserLogin(){return repositoryManager.getUserLogin();}

    public String getApiUrl() { return repositoryManager.getApiUrl(); }

    public String getFragmentMainType(String sParam) { return repositoryManager.getFragmentMainType(sParam); }
}

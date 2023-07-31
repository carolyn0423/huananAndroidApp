package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.Contract.CustomerContract;
import com.hamels.huanan.Repository.Model.Address;
import com.hamels.huanan.Repository.Model.Customer;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;

public class CustomerPresenter extends BasePresenter<CustomerContract.View> implements CustomerContract.Presenter {
    public static final String TAG = CustomerPresenter.class.getSimpleName();

    public CustomerPresenter(CustomerContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);

        getSelectCustomerList();

        getPropertyData();
    }

    private void getSelectCustomerList() {
        repositoryManager.callGetCustomerApi("", "", new BaseContract.ValueCallback<List<Customer>>() {
            @Override
            public void onValueCallback(int task, List<Customer> type) {
                view.setCustomerList(type);
            }
        });
    }

    public void goCustomer(String sCustomerID, String sCustomerName, String sApiUrl) {
        EOrderApplication.CUSTOMER_ID = sCustomerID;
        EOrderApplication.CUSTOMER_NAME = sCustomerName;
        EOrderApplication.sApiUrl = sApiUrl;

        repositoryManager.saveCustomerID(sCustomerID);
        repositoryManager.saveCustomerName(sCustomerName);
        repositoryManager.saveApiUrl(sApiUrl);
        view.goCustomer();
    }

    public Boolean saveLoveCustomerID(String sCustomerID) { return repositoryManager.saveLoveCustomer(sCustomerID); }

    public void saveCustomerID(String sCustomerID) { repositoryManager.saveCustomerID(sCustomerID); }

    public void saveApiUrl(String sApiUrl) { repositoryManager.saveApiUrl(sApiUrl); }

    public void saveSourceActive(String sSourceActive) { repositoryManager.saveSourceActive(sSourceActive); }

    public void goLocationList(String sCustomerID){
        saveCustomerID(sCustomerID);
        view.goLocation(sCustomerID);
    }

    public void goETicketProductMainType(String sCustomerID){
        saveCustomerID(sCustomerID);
        view.goETicketProductMainType(sCustomerID);
    }

    public String getSourceActive() { return repositoryManager.getSourceActive(); }
    public String getLoveCustomer() { return repositoryManager.getLoveCustomer(); }

    public void getCustomerList(final String sMode, String sCity, String sCustomerName, String sLat, String sLon) {
        repositoryManager.callGetCustomerApi(sCity, sCustomerName, new BaseContract.ValueCallback<List<Customer>>() {
            @Override
            public void onValueCallback(int task, List<Customer> type) {
                view.setCustomerList(type);
            }
        });
    }

    public void getPropertyData(){

        String sCustomerID = EOrderApplication.CUSTOMER_ID;

        if(sCustomerID == null || sCustomerID.equals("")){
            repositoryManager.callAdminGetPropertyDataApi( new BaseContract.ValueCallback<List<Address>>() {
                @Override
                public void onValueCallback(int task, List<Address> type) {
                    view.setPropertyCode(type);
                }
            });
        }else{
            repositoryManager.callGetPropertyDataApi( new BaseContract.ValueCallback<List<Address>>() {
                @Override
                public void onValueCallback(int task, List<Address> type) {
                    view.setPropertyCode(type);
                }
            });
        }
    }
}
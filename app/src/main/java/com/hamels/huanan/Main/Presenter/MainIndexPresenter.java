package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.Contract.MainIndexContract;
import com.hamels.huanan.Repository.Model.Carousel;
import com.hamels.huanan.Repository.Model.Customer;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;

public class MainIndexPresenter extends BasePresenter<MainIndexContract.View> implements MainIndexContract.Presenter {
    public static final String TAG = MainIndexPresenter.class.getSimpleName();

    public MainIndexPresenter(MainIndexContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }


    @Override
    public void getCarouselList(final String sCustomer_id) {

        if(EOrderApplication.sApiUrl.equals("") || EOrderApplication.dbConnectName.equals("")){
            checkCustomerNo(EOrderApplication.sPecialCustomerNo);
        }else {
            if (sCustomer_id.equals("")) {
                repositoryManager.callGetAdminCarouselListApi(new BaseContract.ValueCallback<List<Carousel>>() {
                    @Override
                    public void onValueCallback(int task, List<Carousel> type) {
                        view.setCarouselList(type);

                    }
                });
            } else {
                repositoryManager.callGetCarouselListApi(sCustomer_id, new BaseContract.ValueCallback<List<Carousel>>() {
                    @Override
                    public void onValueCallback(int task, List<Carousel> type) {
                        view.setCarouselList(type);

                    }
                });
            }
        }
    }

    @Override
    public String getName() {
        String Name = "";
        if (repositoryManager.getUserLogin()) {
            Name = repositoryManager.getUser().getName();
        }
        else{

        }
        return Name;
    }

    @Override
    public String getGroup() {
        String Group = "";
        if (repositoryManager.getUserLogin()) {
            Group = repositoryManager.getUser().getGroup();
        }
        else{

        }
        return Group;
    }

    @Override
    public void checkMemberData() {
        if(!repositoryManager.getUserID().equals("")){
            if(repositoryManager.getVerifyCode().equals("N")){
                view.getNoVerift();
            }else {
                repositoryManager.callGetMemberInfoApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<User>() {
                    @Override
                    public void onValueCallback(int task, User user) {
                        if (user != null) {
                            if (user.getOnlineEnabled() != null && user.getOnlineEnabled().equals("Y")) {
                                EOrderApplication.WEB_SOCKET_PATH = EOrderApplication.sApiUrl + "/" + EOrderApplication.WEB_SOCKET_PATH_NAME;
                                repositoryManager.saveUser(user);
                                view.setMemberCardImg(user.getGroup());
                                view.CallActive();
                            }
                        } else {
                            view.CustomerOnlineISFalse();
                        }
                    }
                });
            }
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin();}

    public String getMobile() { return repositoryManager.getMobile(); }

    public void checkCustomerNo(String customer_no) {
        if (customer_no.isEmpty()) {
            view.showToast("EmptyCustomerNo");
        } else {
            repositoryManager.callGetCustomerByNoApi(customer_no, new BaseContract.ValueCallback<Customer>() {
                @Override
                public void onValueCallback(int task, Customer customers) {
                    if(customers.getCustomerID() == null || customers.getCustomerID().equals("")){
                        view.showToast("EmptyCustomerNo");
                    }else{
                        String sLoveCustomer = repositoryManager.getLoveCustomer();
                        if(sLoveCustomer.indexOf("|" + customers.getCustomerID() + "|") == -1) {
                            repositoryManager.saveLoveCustomer(customers.getCustomerID());

                            //view.showToast("AddLove");
                        }

                        EOrderApplication.CUSTOMER_ID = customers.getCustomerID();
                        EOrderApplication.CUSTOMER_NAME = customers.getCustomerName();
                        EOrderApplication.sApiUrl = customers.getApiUrl();

                        repositoryManager.saveCustomerID(customers.getCustomerID());
                        repositoryManager.saveCustomerName(customers.getCustomerName());
                        repositoryManager.saveApiUrl(customers.getApiUrl());

                        getCarouselList(customers.getCustomerID());
                    }
                }
            });
        }
    }

    public void logout() {
        repositoryManager.callLogOutApi(repositoryManager.getUserID(), new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {

            }
        });
    }
}

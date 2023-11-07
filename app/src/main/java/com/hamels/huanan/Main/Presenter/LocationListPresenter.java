package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Main.Contract.LocationListContract;
import com.hamels.huanan.Main.View.LocationFragment;
import com.hamels.huanan.Repository.Model.Store;
import com.hamels.huanan.Repository.RepositoryManager;

import java.util.List;

public class LocationListPresenter extends BasePresenter<LocationListContract.View> implements LocationListContract.Presenter {
    public static final String TAG = LocationListPresenter.class.getSimpleName();

    private String functionname, location_id;

    public LocationListPresenter(LocationListContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    public void goProductMainType(String sLocationID){ view.goProductMainType(sLocationID); }

    public void goLocationDesc(Store store){ view.goLocationDesc(store); }

    public void eventKeyword() {
        view.getSearchKeyword();
    }

    public void getLocationList(String sKeyword) {
        repositoryManager.callGetLocationApi("AppLocation", repositoryManager.getCustomerID(), "", "0", "", sKeyword, new BaseContract.ValueCallback<List<Store>>() {
            @Override
            public void onValueCallback(int task, List<Store> type) {
                if(sKeyword.equals("")) {
                    boolean isOnlineShopping = type.size() > 0 ? true : false;
                    view.setOnlineShoppingFlag(isOnlineShopping, type);
                }
                view.setLocationList(type);
            }
        });
    }

    @Override
    public void setStoreOften(String location_id, String uid) {
        if(!repositoryManager.getUserID().equals("")) {
            repositoryManager.callSetLocationOftenApi(location_id, uid, new BaseContract.ValueCallback<Boolean>() {
                @Override
                public void onValueCallback(int task, Boolean bSuccess) {
                    getLocationList("");
                }
            });
        }
    }

    public boolean getUserLogin(){ return repositoryManager.getUserLogin();}

    public String getSourceActive() { return repositoryManager.getSourceActive(); }

    public String getFragmentLocation() { return repositoryManager.getFragmentLocation(); }

    public void saveSourceActive(String sSourceActive) { repositoryManager.saveSourceActive(sSourceActive); }

    public void saveFragmentMainType(String sLocationID, String IsETicket) { repositoryManager.saveFragmentMainType(sLocationID, IsETicket); }

}

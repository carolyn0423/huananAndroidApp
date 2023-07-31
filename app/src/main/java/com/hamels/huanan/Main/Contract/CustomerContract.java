package com.hamels.huanan.Main.Contract;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Repository.Model.Address;
import com.hamels.huanan.Repository.Model.Customer;

import java.util.List;

public interface CustomerContract {
    interface View extends BaseContract.View {

        void setPropertyCode(List<Address> type);

        void setCustomerList(List<Customer> type);

        void goCustomer();

        void goLocation(String sCustomerID);

        void goETicketProductMainType(String sCustomerID);
    }

    interface Presenter extends BaseContract.Presenter {

        String getSourceActive();

        void goCustomer(String customerID, String customerName, String apiUrl);

        Boolean saveLoveCustomerID(String customerID);

        void saveCustomerID(String customerID);

        void saveApiUrl(String sApiUrl);

        void goLocationList(String customerID);

        void goETicketProductMainType(String customerID);

        void saveSourceActive(String sSourceActive);
    }
}

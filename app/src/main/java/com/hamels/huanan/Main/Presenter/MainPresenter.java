package com.hamels.huanan.Main.Presenter;

import com.hamels.huanan.Base.BaseContract;
import com.hamels.huanan.Base.BasePresenter;
import com.hamels.huanan.Business.View.BusinessFragment;
import com.hamels.huanan.Donate.View.DonateFragment;
import com.hamels.huanan.DrawLots.View.DrawLotsFragment;
import com.hamels.huanan.Main.Contract.MainContract;

import com.hamels.huanan.Main.View.MemberCardFragment;
import com.hamels.huanan.Main.View.MemberCenterFragment;

import com.hamels.huanan.Main.View.MessageFragment;
import com.hamels.huanan.Main.View.ShoppingCartFragment;
import com.hamels.huanan.MemberCenter.View.MailDetailFragment;
import com.hamels.huanan.MemberCenter.View.MailFileFragment;
import com.hamels.huanan.MemberCenter.View.MessageListFragment;
import com.hamels.huanan.MemberCenter.View.WebViewFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.ApiCallback;
import com.hamels.huanan.Repository.ApiRepository.MemberRepository;
import com.hamels.huanan.Repository.Model.BaseModel;
import com.hamels.huanan.Repository.Model.Customer;
import com.hamels.huanan.Repository.Model.Store;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.Repository.RepositoryManager;
import com.hamels.huanan.EOrderApplication;

import static com.hamels.huanan.Constant.ApiConstant.TASK_POST_READ_MESSAGE;
import static com.hamels.huanan.Constant.Constant.REQUEST_COUPON;
import static com.hamels.huanan.Constant.Constant.REQUEST_DONATE;
import static com.hamels.huanan.Constant.Constant.REQUEST_LOT_LIST;
import static com.hamels.huanan.Constant.Constant.REQUEST_MAIL;
import static com.hamels.huanan.Constant.Constant.REQUEST_MEMBER_CARD;
import static com.hamels.huanan.Constant.Constant.REQUEST_MEMBER_CENTER;
import static com.hamels.huanan.Constant.Constant.REQUEST_MESSAGE;
import static com.hamels.huanan.Constant.Constant.REQUEST_BUSINESS;
import static com.hamels.huanan.Constant.Constant.REQUEST_SHOPPING_CART;
import static com.hamels.huanan.Constant.Constant.REQUEST_MESSAGE_LIST;

import android.content.Context;

import java.util.List;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public static final String TAG = MainPresenter.class.getSimpleName();
    public MainPresenter(MainContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }
    @Override
    public void getMailBadgeFromApi() {
        if (repositoryManager.getUserLogin()) {
            repositoryManager.callGetMailBadgeApi(new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.setMailBadge(type);
                }
            });
        } else {
            view.setMailBadge("0");
        }
    }

    @Override
    public void getMessageBadgeFromApi() {
        if (repositoryManager.getUserLogin()) {
            repositoryManager.callGetMessageBadgeApi(new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.setMessageBadge(type);
                }
            });
        } else {
            view.setMessageBadge("0");
        }
    }

    public void setCustomerData(){
        //  初始設定
        repositoryManager.saveCustomerID(EOrderApplication.CUSTOMER_ID);
        repositoryManager.saveCustomerName(EOrderApplication.CUSTOMER_NAME);
        repositoryManager.saveCustomerName(EOrderApplication.sApiUrl);

        if(EOrderApplication.sApiUrl.equals("")){
            checkCustomerNo(EOrderApplication.sPecialCustomerNo);
        }
    }

    @Override
    public void getBadgeNumberFromApi() {
        if (repositoryManager.getUserLogin()) {
            repositoryManager.callGetBadgeNumberApi(new BaseContract.ValueCallback<String>() {
                @Override
                public void onValueCallback(int task, String type) {
                    view.setAllBadge(type);

                    String[] array= type.split("_");
                    if(array.length==4){
                        repositoryManager.saveShoppingCartCount(array[3]);
                    }else{
                        repositoryManager.saveShoppingCartCount("0");
                    }
                }
            });
        } else {
            view.setAllBadge("0_0_0_0");
        }
    }

    public void getLocationList() {
        String sCustomerID = repositoryManager.getCustomerID();

        //  先取得該商家的全門市清單
        repositoryManager.callGetLocationApi("AppLocation", sCustomerID, "", "0", "", "", new BaseContract.ValueCallback<List<Store>>() {
            @Override
            public void onValueCallback(int task, List<Store> type) {
                view.ProductLocationFragment(type.size());
            }
        });
    }

    @Override
    public void checkLoginForMemberCard() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(MemberCardFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MEMBER_CARD);
        }
    }

    @Override
    public void checkLoginForCoupon() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(WebViewFragment.getInstance(R.string.coupon, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_COUPONS_URL));
        } else {
            view.intentToLogin(REQUEST_COUPON);
        }
    }

    @Override
    public void checkLoginForLot() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(DrawLotsFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_LOT_LIST);
        }
    }

    @Override
    public void checkLoginForBusiness() {
        if (repositoryManager.getUserLogin()) {
            view.addFragment(BusinessFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_BUSINESS);
        }
    }

    @Override
    public void goNewsDetail(String news_id) {
        view.addFragment(WebViewFragment.getInstance(R.string.tab_news, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_NEWS_URL,news_id));
    }

    @Override
    public User JsCallGetMemberInfo() {
        return repositoryManager.getUser();
    }

    @Override
    public void checkLoginForMemberCenter() {
        if (repositoryManager.getUserLogin()) {
            view.changeTabFragment(MemberCenterFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MEMBER_CENTER);
        }
    }

    @Override
    public void checkLoginForMessage() {
        if (repositoryManager.getUserLogin()) {
            view.changeTabFragment(MessageFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MESSAGE);
        }
    }

    @Override
    public void checkLoginForMessageList() {
        if (repositoryManager.getUserLogin()) {
            view.changeTabFragment(MessageListFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MESSAGE_LIST);
        }
    }

    @Override
    public void checkLoginForShoppingCart(String orderType) {
        if (repositoryManager.getUserLogin()) {
            view.changeTabFragment(ShoppingCartFragment.getInstance(orderType));
        } else {
            view.intentToLogin(REQUEST_SHOPPING_CART);
        }
    }

    @Override
    public void checkLoginForMail(String fragmentName) {
        if (fragmentName.equals(MailFileFragment.TAG) || fragmentName.equals(MailDetailFragment.TAG)) {
            return;
        }
        if (repositoryManager.getUserLogin()) {
            view.addFragment(MailFileFragment.getInstance());
            //view.addFragment(MainIndexFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MAIL);
        }
    }

    @Override
    public void checkLoginForMessage(String fragmentName) {
        if (fragmentName.equals(MessageListFragment.TAG)) {
            return;
        }

        if (repositoryManager.getUserLogin()) {
            view.addFragment(MessageListFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_MESSAGE);
        }
    }

    @Override
    public void checkLoginForDonate() {
        if (repositoryManager.getUserLogin()) {
            DonateFragment.getInstance().type_idMode("0");
            view.addFragment(DonateFragment.getInstance());
        } else {
            view.intentToLogin(REQUEST_DONATE);
        }
    }

//    @Override
//    public void checkLoginForIndex() {
//        if (repositoryManager.getUserLogin()) {
////            view.addFragment(DonateFragment.getInstance());
//        } else {
//            view.intentToLogin(REQUEST_DONATE);
//        }
//    }

    public Boolean getUserLogin() { return repositoryManager.getUserLogin();}

    public String getSourceActive() { return repositoryManager.getSourceActive(); }

    public String getCustomerID() { return repositoryManager.getCustomerID(); }

    public String getApiUrl() { return repositoryManager.getApiUrl(); }

    public String getInvitationCode() {
        return repositoryManager.getInvitationCode();
    }

    public String getUserName() {
        return repositoryManager.getUserName();
    }

    public String getUserAccount() { return repositoryManager.getUserAccount(); }

    public String getUserPw() { return repositoryManager.getUserPassword(); }

    public void saveSourceActive(String sSourceActive) { repositoryManager.saveSourceActive(sSourceActive); }

    public void saveFragmentMainType(String sLocationID, String IsETicket) { repositoryManager.saveFragmentMainType(sLocationID, IsETicket); }

    public void saveFragmentLocation(String sLocationID) { repositoryManager.saveFragmentLocation(sLocationID); }

    @Override
    public void logout() {
        repositoryManager.callLogOutApi(repositoryManager.getUserID(),new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                checkLoginForMemberCenter();
                view.setAllBadge("0_0_");
            }
        });
    }
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
                        EOrderApplication.dbConnectName = customers.getdbConnectName();

                        repositoryManager.saveCustomerID(customers.getCustomerID());
                        repositoryManager.saveCustomerName(customers.getCustomerName());
                        repositoryManager.saveApiUrl(customers.getApiUrl());

                        //  自動重新登入
                        if(!getUserAccount().equals("") && !getUserPw().equals("")) {
                            repositoryManager.callLoginApi(customers.getCustomerID(), getUserAccount(), getUserPw(), new BaseContract.ValueCallback<User>() {
                                @Override
                                public void onValueCallback(int task, User user) {
                                    repositoryManager.saveCustomerID(Integer.toString(user.getCustomer()));
                                    repositoryManager.saveAccountInfo(getUserAccount(), getUserPw());
                                    repositoryManager.saveUserID(Integer.toString(user.getMember()));
                                    repositoryManager.saveVerifyCode(user.getVerifyCode());
                                    repositoryManager.saveApiUrl(customers.getApiUrl());
                                }
                            }, new BaseContract.ValueCallback<String>() {
                                @Override
                                public void onValueCallback(int task, String message) {
                                    repositoryManager.saveAccountInfo("", "");
                                    repositoryManager.saveUserID("");
                                    repositoryManager.saveVerifyCode("");

                                    getCustomer();
                                }
                            });
                        }else{
                            repositoryManager.saveAccountInfo("", "");
                            repositoryManager.saveUserID("");
                            repositoryManager.saveVerifyCode("");

                            getCustomer();
                        }
                    }
                }
            });
        }
    }

    public void getCustomer(){
        String sCustomerID = repositoryManager.getCustomerID();
        String sLoveCustomer = repositoryManager.getLoveCustomer();
        String[] LoveCustomer = sLoveCustomer.split("\\|");

        if(sCustomerID.equals((""))){
            EOrderApplication.CUSTOMER_ID = "";
            EOrderApplication.CUSTOMER_NAME = "";
            EOrderApplication.sApiUrl = "";
            if(LoveCustomer.length > 0 && !sLoveCustomer.equals((""))){
                repositoryManager.callGetCustomerDetailApi(LoveCustomer[1], new BaseContract.ValueCallback<Customer>() {
                    @Override
                    public void onValueCallback(int task, Customer customers) {
                        view.setCustomer(customers, "", "", "");
                    }
                });
            }else{
                view.setCustomer(null, "", "", "");
            }
        }else{
            view.setCustomer(null, sCustomerID, repositoryManager.getCustomerName(), repositoryManager.getApiUrl());
        }
    }

    public void getOnlineVision(){
        repositoryManager.callGetCustomerDetailApi(EOrderApplication.CUSTOMER_ID, new BaseContract.ValueCallback<Customer>() {
            @Override
            public void onValueCallback(int task, Customer customers) {
                view.getVersion(customers.getAppstoreVersion());
            }
        });

//        repositoryManager.callGetOnlineVision("android_huanan_appstore_version", new BaseContract.ValueCallback<WebSetup>() {
//            @Override
//            public void onValueCallback(int task, WebSetup websetup) {
//                view.getVersion(websetup.getSysContent());
//            }
//        });
    }

    public void GetShopCartLocationQuantity() {
        repositoryManager.GetShopCartLocationQuantity(new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String type) {
                view.ShoppingBackPage(type);
            }
        });
    }
}

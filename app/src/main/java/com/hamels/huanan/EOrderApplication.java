package com.hamels.huanan;

import android.app.Application;

public class EOrderApplication extends Application {
    public static final String TAG = EOrderApplication.class.getSimpleName();

    public static final boolean isPrd = false;

    //public static final String DOMAIN_SIT = "https://eorder.hamels.com.tw:9903/";
    //public static final String DOMAIN_UAT = "https://eorder.hamels.com.tw:9920/";
    //public static final String DOMAIN_PRD = "https://eorder.hamels.com.tw:9920/";
    public static String CUSTOMER_ID = "110";  //暫時先hardcode
    public static String CUSTOMER_NAME = "學田泉";
    public static final String DOMAIN_PRO = "https://management.maverick.com.tw";
    public static final String DOMAIN_UAT = "https://www.hamels.com.tw:9941/";
    public static final String DOMAIN_SIT = "https://eorder.hamels.com.tw:9906/";

//    public static final String DOMAIN_ADMIN_PRO = "https://management.maverick.com.tw";
//    public static final String DOMAIN_ADMIN_UAT = "https://www.hamels.com.tw:9941/";
//    public static final String DOMAIN_ADMIN_SIT = "https://eorder.hamels.com.tw:9940/";
//    public static final String
    //public static String DOMAIN = isPrd ? DOMAIN_ADMIN_UAT : DOMAIN_ADMIN_SIT;
    //public static String ADMIN_DOMAIN = isPrd ? DOMAIN_ADMIN_UAT : DOMAIN_ADMIN_SIT;
    public static String DOMAIN = isPrd ?  DOMAIN_UAT: DOMAIN_SIT;
    public static String ADMIN_DOMAIN = isPrd ? DOMAIN_UAT : DOMAIN_SIT;
    public static String sApiUel = isPrd ? DOMAIN_UAT : DOMAIN_SIT;
    public static String WEBVIEW_DOMAIN = DOMAIN;
    public static String WEBVIEW_COUPONS_URL = WEBVIEW_DOMAIN + "/coupon.html";
    public static String WEBVIEW_TERMS_URL = WEBVIEW_DOMAIN + "/faq.html?id=1";
    public static String WEBVIEW_LOCATION_URL = WEBVIEW_DOMAIN + "/location.html";
    public static String WEBVIEW_SHOPPING_CART_URL = WEBVIEW_DOMAIN + "/shoppingcart_list.html";
    public static String WEBVIEW_SHOPPING_CART_URL2 = WEBVIEW_DOMAIN + "/shoppingcart_list.html?ordertype=E";
    public static String WEBVIEW_NEWS_URL = WEBVIEW_DOMAIN + "/news_detail.html";
    public static String WEBVIEW_SMILEPOINT_URL = WEBVIEW_DOMAIN + "/my_points.html";
    public static String WEBVIEW_SIZE_SPEC_URL = WEBVIEW_DOMAIN + "/product_spec.html";
    public static String WEBVIEW_MESSAGE_URL = WEBVIEW_DOMAIN + "/message_board.html";
    public static String WEBVIEW_PUSHMAIL_URL = WEBVIEW_DOMAIN + "/push_folder.html";
    public static String WEBVIEW_ORDER_URL = WEBVIEW_DOMAIN + "/order.html";
    public static String WEBVIEW_ORDERDETAIL_URL = WEBVIEW_DOMAIN + "/orderDetail.html";

    public static double lat = 0, lon = 0;
    public static boolean isLogin = false;

    private static EOrderApplication application;

    public static EOrderApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static void setUrl(String sUrl){
        DOMAIN = sUrl;
        WEBVIEW_DOMAIN = sUrl;
        WEBVIEW_COUPONS_URL = sUrl + "/coupon.html";
        WEBVIEW_TERMS_URL = sUrl + "/faq.html?id=1";
        WEBVIEW_LOCATION_URL = sUrl + "/location.html";
        WEBVIEW_SHOPPING_CART_URL = sUrl + "/shoppingcart_list.html";
        WEBVIEW_SHOPPING_CART_URL2 = sUrl + "/shoppingcart_list.html?ordertype=E";
        WEBVIEW_NEWS_URL = sUrl + "/news_detail.html";
        WEBVIEW_SMILEPOINT_URL = sUrl + "/my_points.html";
        WEBVIEW_SIZE_SPEC_URL = sUrl + "/product_spec.html";
        WEBVIEW_MESSAGE_URL = sUrl + "/message_board.html";
        WEBVIEW_PUSHMAIL_URL = sUrl + "/push_folder.html";
        WEBVIEW_ORDER_URL = sUrl + "/order.html";
        WEBVIEW_ORDERDETAIL_URL = sUrl + "/orderDetail.html";
    }
}

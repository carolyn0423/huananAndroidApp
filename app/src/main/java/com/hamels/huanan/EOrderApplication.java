package com.hamels.huanan;

import android.app.Application;

public class EOrderApplication extends Application {
    public static final String TAG = EOrderApplication.class.getSimpleName();

    public static final boolean isPrd = false;

    //public static final String DOMAIN_SIT = "https://eorder.hamels.com.tw:9903/";
    //public static final String DOMAIN_UAT = "https://eorder.hamels.com.tw:9920/";
    //public static final String DOMAIN_PRD = "https://eorder.hamels.com.tw:9920/";
    public static final String sPecialCustomerNo = "45GPZ";

    public static final String DOMAIN_ADMIN_PRO = "https://newmanagement.maverick.com.tw";
    public static final String DOMAIN_ADMIN_UAT = "https://www.hamels.com.tw:9941/";
    public static final String DOMAIN_ADMIN_SIT = "https://eorder.hamels.com.tw:9940/";

    //public static String ADMIN_DOMAIN = isPrd ? DOMAIN_ADMIN_PRO : DOMAIN_ADMIN_SIT;
    public static String ADMIN_DOMAIN = DOMAIN_ADMIN_UAT;
    
    public static String CUSTOMER_ID = "110";
    public static String CUSTOMER_NAME = "學田泉";
    public static String sApiUrl = "";
    public static String WEB_SOCKET_PATH = "";
    public static String WEB_SOCKET_PATH_NAME = "WebSocketHandler.ashx";
    public static String WEB_SOCKET_MOBILE = "";
    public static String WEB_SOCKET_MOBILE_CHK = "";
    public static boolean isLogin = false;
    public static String dbConnectName = "";
    //  購物車數
    public static String cartBadgeCount = "0";
    //  票卷購物車數
    public static String cartTicketBadgeCount = "0";
    //  票卷數
    public static String memberTicketBadgeCount = "0";
    //  訊息
    public static String mailBadgeCount = "0";
    //  客服
    public static String messageBadgeCount = "0";
    public static String LOCATION_NAME = "";
    public static String WEBVIEW_WHATCOFFEE_URL = "/what_coffee.html";
    public static String WEBVIEW_COUPONS_URL = "/coupon.html";
    public static String WEBVIEW_TERMS_URL = "/faq.html?id=1";
    public static String WEBVIEW_LOCATION_URL = "/location.html";
    public static String WEBVIEW_SHOPPING_CART_URL = "/shoppingcart_list.html";
    public static String WEBVIEW_SHOPPING_CART_URL2 = "/shoppingcart_list.html?ordertype=E";
    public static String WEBVIEW_NEWS_URL = "/news_detail.html";
    public static String WEBVIEW_SMILEPOINT_URL = "/my_points.html";
    public static String WEBVIEW_SIZE_SPEC_URL = "/product_spec.html";
    public static String WEBVIEW_MESSAGE_URL = "/message_board.html";
    public static String WEBVIEW_PUSHMAIL_URL = "/push_folder.html";
    public static String WEBVIEW_ORDER_URL = "/order.html";
    public static String WEBVIEW_ORDERDETAIL_URL = "/orderDetail.html";
    public static String WEBVIEW_PAY_COMPLETE_URL = "/pay_complete.html";
    public static String WEBVIEW_CONTENT_URL = "/content.html";
    public static String DEFAULT_PICTURE_URL = "/UploadImages/Product/default.png";
    public static String MESSAGE_TAG = "";

    public static double lat = 0, lon = 0;

    private static EOrderApplication application;

    public static EOrderApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}

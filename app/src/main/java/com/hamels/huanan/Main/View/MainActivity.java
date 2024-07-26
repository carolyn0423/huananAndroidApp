package com.hamels.huanan.Main.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hamels.huanan.Donate.View.DonateFragment;
import com.hamels.huanan.MemberCenter.View.AboutFragment;
import com.hamels.huanan.MemberCenter.View.AdminMessageFragment;
import com.hamels.huanan.MemberCenter.View.MemberInfoChangeFragment;
import com.hamels.huanan.MemberCenter.View.MemberPointFragment;
import com.hamels.huanan.MemberCenter.View.PasswordChangeFragment;
import com.hamels.huanan.Product.View.ProductDetailFragment;
import com.hamels.huanan.Product.View.ProductMainTypeFragment;
import com.hamels.huanan.Repository.ApiRepository.ApiRepository;
import com.hamels.huanan.Repository.ApiRepository.MemberRepository;
import com.hamels.huanan.Repository.Model.Customer;
import com.hamels.huanan.Repository.Model.MessageEvent;
import com.hamels.huanan.Utils.CustomBottomNavigationView;
import com.hamels.huanan.Utils.SharedUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Business.View.BusinessFragment;
import com.hamels.huanan.Business.View.BusinessProductFragment;
import com.hamels.huanan.DrawLots.View.DrawLotsFragment;
import com.hamels.huanan.Main.Contract.MainContract;
import com.hamels.huanan.Main.Presenter.MainPresenter;
import com.hamels.huanan.MemberCenter.View.MailFileFragment;
import com.hamels.huanan.MemberCenter.View.MessageListFragment;
import com.hamels.huanan.MemberCenter.View.WebViewFragment;
import com.hamels.huanan.Product.View.ProductFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.User;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hamels.huanan.Constant.Constant.REQUEST_COUPON;
import static com.hamels.huanan.Constant.Constant.REQUEST_MAIL;
import static com.hamels.huanan.Constant.Constant.REQUEST_MAIN_INDEX;
import static com.hamels.huanan.Constant.Constant.REQUEST_MEMBER_CARD;
import static com.hamels.huanan.Constant.Constant.REQUEST_MEMBER_CENTER;
import static com.hamels.huanan.Constant.Constant.REQUEST_MESSAGE;
import static com.hamels.huanan.Constant.Constant.REQUEST_POINT;
import static com.hamels.huanan.Constant.Constant.REQUEST_SHOPPING_CART;
import static com.hamels.huanan.Constant.Constant.REQUEST_BUSINESS;
import static com.hamels.huanan.Constant.Constant.REQUEST_LOT_LIST;
import static com.hamels.huanan.Constant.Constant.REQUEST_DONATE;
import static com.hamels.huanan.Constant.Constant.REQUEST_MESSAGE_LIST;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends BaseActivity implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName();
    private final int BACK_STACK_CLEAR = 0;
    private final int FRAGMENT_REMOVE = 1;

    private BaseFragment willChangeFragment;
    // navigation
    private CustomBottomNavigationView bottomNavigationView;
    private MainContract.Presenter mainPresenter;
    private WebView webView;
    private static TextView tvShoppingCart, tvShoppingCartETicket, tvMessageUnread;

    // toolbar
    private ConstraintLayout constClose, constBackground, constBase;
    private LinearLayout llOpen, llItemButton;

    // navigation
    private FloatingActionButton btnShopping;
    private LinearLayout layoutHome, layoutOrder, layoutPurchase, layoutMember;
    private ImageView imgHome, imgOrder, imgPurchase, imgMember;
    private TextView txtHome, txtOrder, txtPurchase, txtMember;

    // qrcode
    private PopupWindow popupWindow;
    private ImageView dialog_img_qrcode;
    private int barcodeWidth = 250;
    private int barcodeHeight = 250;
    private int brightnessNow = 0;
    private TextView text_invite_code;

    public static String sSourceActive = "";
    public static String sCustomerID = "";
    private Boolean isUpdate = false;
    private int VersionCode = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String sPDFDir = "";
    private float originalBrightness = -1; // 保存原始亮度值
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BACK_STACK_CLEAR:
                    removeExistFragment();
                    break;
                case FRAGMENT_REMOVE:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, willChangeFragment, "").commit();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //  WebSocket
    private WebSocket webSocket;
    private OkHttpClient client;
    private Gson gson;

    public void WebSocketManager() {
        gson = new Gson();
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getExtras() != null){
            String OrderData = getRepositoryManager(this).getPaySchemeOrderData();
            if(!OrderData.equals("")){
                String[] oData = OrderData.split("\\|");
                savePaySchemeOrderData("");
                goOrderPage(oData[0], oData[1], oData[2]);
            } else {
                notifyChangeToMail();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));

        //mainPresenter.saveSourceActive("");

        sSourceActive = mainPresenter.getSourceActive();

        sCustomerID = EOrderApplication.CUSTOMER_ID;

        setCustomerData();
        initView();
        initAnimation(this);

        WebSocketManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable(){
            @Override
            public void run() {
//                if(EOrderApplication.isPrd){
//                    try {
//                        VersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
//
//                        ChkVision();
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }

                try {
                    VersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                    ChkVision();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void setCustomerID(String CustomerID) {
        sCustomerID = CustomerID;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        refreshBadge();

        // 版本更新提醒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "isUpdate : " + isUpdate);
                if(isUpdate){
                    AlertUpdateApp();
                }
            }
        }, 3000);
    }

    private void notifyChangeToMail() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (fragment != null) {
            mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, MainIndexFragment.getInstance(), "").commit();
            mainPresenter.checkLoginForMail(MainIndexFragment.TAG);
        }
    }

    private void initView() {
        SharedUtils.getInstance().saveCustomerID(EOrderApplication.getInstance(), EOrderApplication.CUSTOMER_ID);
        sSourceActive = mainPresenter.getSourceActive();

        //  toolbar
        llOpen = findViewById(R.id.ll_title_open);
        llItemButton = findViewById(R.id.ll_item_button);
        constClose = findViewById(R.id.const_title_close);
        constBackground = findViewById(R.id.const_background);
        constBase = findViewById(R.id.const_base);

        tvShoppingCart = findViewById(R.id.tv_shopping_cart);
        //tvShoppingCartETicket = findViewById(R.id.tv_shopping_cart_e_ticket);
        tvMessageUnread = findViewById(R.id.tv_message_unread);

        // floating_action_bar
        //btnShopping = (FloatingActionButton) findViewById(R.id.floating_action_bar);
        //btnShopping.setOnClickListener(onClickListener);

        // navigation
        layoutHome = findViewById(R.id.home);
        layoutHome.setOnClickListener(onClickListener);
        layoutOrder = findViewById(R.id.order);
        layoutOrder.setOnClickListener(onClickListener);
        layoutPurchase = findViewById(R.id.purchase);
        layoutPurchase.setOnClickListener(onClickListener);
        layoutMember = findViewById(R.id.member);
        layoutMember.setOnClickListener(onClickListener);
        imgHome = findViewById(R.id.img_home);
        imgOrder = findViewById(R.id.img_order);
        imgPurchase = findViewById(R.id.img_purchase);
        imgMember = findViewById(R.id.img_member);
        txtHome = findViewById(R.id.txt_home);
        txtOrder = findViewById(R.id.txt_order);
        txtPurchase = findViewById(R.id.txt_purchase);
        txtMember = findViewById(R.id.txt_member);

        LinearLayout qrcode = findViewById(R.id.qrcode);
        qrcode.setOnClickListener(onClickListener);
        LinearLayout message = findViewById(R.id.message);
        message.setOnClickListener(onClickListener);
        LinearLayout what_coffee = findViewById(R.id.what_coffee);
        what_coffee.setOnClickListener(onClickListener);
        //LinearLayout donate = findViewById(R.id.donate);
        //donate.setOnClickListener(onClickListener);

        setAppToolbar(R.id.toolbar);
        setBottomNavigation(R.id.tab_bar);
        setCartBadge(R.id.tv_shopping_cart);

        appToolbar.getBtnMail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
            }
        });
        appToolbar.getBtnMessage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EOrderApplication.MESSAGE_TAG = "";
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMessage(fragment.getClass().getSimpleName());
                appToolbar.setMessageBadgeCount(Integer.parseInt("0"));
            }
        });

        appToolbar.getBtnSort().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = -1;
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                // 交易紀錄
                if (TransRecordFragment.getInstance().isVisible()) {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_sort_order, popupMenu.getMenu());
                    page = 1;
                }
                // 商品列表
                else if (ProductFragment.getInstance().isVisible()) {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());
                    page = 2;
                }
                // 企業商品列表
                else if (BusinessProductFragment.getInstance().isVisible()) {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_sort, popupMenu.getMenu());
                    page = 3;
                }
                final int mPage = page;
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String sort = "NEW";
                        String filter = "";
                        int id = item.getItemId();

                        // product
                        if (id == R.id.item_CHEAP){
                            sort = "CHEAP";
                        }else if (id == R.id.item_EXPENSIVE){
                            sort = "EXPENSIVE";
                        }else if (id == R.id.item_NEW){
                            sort = "NEW";
                        }

                        // order
                        else if (id == R.id.item_order_status_all){
                            filter = "";
                        }else if (id == R.id.item_order_status_1){
                            filter = "1";
                        }else if (id == R.id.item_order_status_2){
                            filter = "2";
                        }else if (id == R.id.item_order_status_3){
                            filter = "3";
                        }else if (id == R.id.item_order_status_4){
                            filter = "4";
                        }
                        switch (mPage) {
                            case 1: //order
                                TransRecordFragment.getInstance().orderFilterMode(filter);
                                break;
                            case 2: //product
                                ProductFragment.getInstance().SortMode(sort);
                                break;
                            case 3: //business product
                                BusinessProductFragment.getInstance().SortMode(sort);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        refreshBadge();
        bottomNavigationView = findViewById(R.id.navigation);
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            bottomNavigationView.setIconScaleType(menuItem);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            switch (item.getItemId()) {
                case R.id.item_news:
                    setMainIndexMessageUnreadVisibility(true);
                    changeTabFragment(MainIndexFragment.getInstance());
                    return true;
                case R.id.item_shop:
                    setMainIndexMessageUnreadVisibility(false);
                    checkMerchantCount("PRODUCT", "N"); //非電子商品
                    return true;
                case R.id.item_member_card:
                    setMainIndexMessageUnreadVisibility(false);
                    mainPresenter.checkLoginForMemberCenter();
                    return true;
                case R.id.item_location:
                    setMainIndexMessageUnreadVisibility(false);
                    mainPresenter.saveFragmentLocation("");
                    changeTabFragment(LocationFragment.getInstance("Menu"));
                    return true;
            }
            return false;
        });

        //bottomNavigationViewEx.setCurrentItem(0);
        //====for notification 當此MainActivity是由FcmService呼叫帶起時,要直接將畫面轉至訊息中心========
        Intent intent = getIntent();
        String notifyExtra = intent.getStringExtra("NOTIFY_EXTRA"); //前景才會有值
        String notifyExtraBg = getIntent().getExtras().getString("click_action");  // 背景才會有值

        if ((notifyExtra != null && notifyExtra.equals("NOTIFY")) || (null != notifyExtraBg && notifyExtraBg.equals("NOTIFY_EXTRA"))) {
            notifyChangeToMail();
        } else {
            switch (sSourceActive){
                case "PRODUCT_WELCOME":
                case "LOCATION_WELCOME":
                    //  外帶外送 未登入的情況
                    if(sSourceActive.equals("PRODUCT_WELCOME")){
                        changeNavigationColor(R.id.order);
                    }else{
                        changeNavigationColor(R.id.home);
                    }
                    mainPresenter.saveSourceActive("");
                    mainPresenter.saveFragmentLocation("");
                    changeTabFragment(LocationFragment.getInstance("Menu"));
                    break;
                case "ETICKET_WELCOME":
                    //  買提貨卷 未登入的情況
                    changeNavigationColor(R.id.purchase);
                    mainPresenter.saveSourceActive("");
                    mainPresenter.saveFragmentMainType("", "Y");
                    changeTabFragment(ProductMainTypeFragment.getInstance());
                    break;
                default:
                    MainIndexFragment fragment = new MainIndexFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.commit();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MenuItem menuItem = bottomNavigationView.getMenu().getItem(0);
                            bottomNavigationView.setIconScaleType(menuItem);
                        }
                    }, 500);
                    break;
            }
        }

        //  取得螢幕權限
        //checkCanWritePermission();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            /*下方menu*/
            if (id == R.id.home){ //首頁
                changeNavigationColor(v.getId());
                changeTabFragment(MainIndexFragment.getInstance());
            }else if (id == R.id.order){
                //  外帶外送
                changeNavigationColor(v.getId());
                mainPresenter.saveSourceActive("");
                checkMerchantCount("PRODUCT", "N"); //非電子商品

            }else if (id == R.id.purchase){
                //  買提貨券
                changeNavigationColor(v.getId());
                mainPresenter.checkLoginForShoppingCart("G");
            }else if (id == R.id.member){ //會員中心
                changeNavigationColor(v.getId());
                mainPresenter.checkLoginForMemberCenter();
            }
            /*首頁上方menu*/
            else if (id == R.id.qrcode){
                if (mainPresenter.getUserLogin()) {
                    try {
                        originalBrightness = Settings.System.getInt(getContentResolver(),
                                Settings.System.SCREEN_BRIGHTNESS);
                    } catch (Settings.SettingNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    // 设置屏幕亮度为最亮
                    setScreenBrightnessToMax();

                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_qrcode, null);

                    popupWindow = new PopupWindow(view);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                    dialog_img_qrcode = view.findViewById(R.id.dialog_img_qrcode);
                    text_invite_code = view.findViewById(R.id.text_invite_code);

                    User user = getUser();
                    createQRcodeImage(user.getMembershipCode(), dialog_img_qrcode);
                    text_invite_code.setText(mainPresenter.getInvitationCode());


                    Button dialog_btn_close = view.findViewById(R.id.dialog_btn_close);
                    dialog_btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            changeAppBrightness(brightnessNow);
                        }
                    });

                    text_invite_code.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OpenShared();
                        }
                    });

                    popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    intentToLogin(REQUEST_MAIN_INDEX);
                }
            }else if (id == R.id.message){
                setMainIndexMessageUnreadVisibility(false);
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                mainPresenter.checkLoginForMail(fragment.getClass().getSimpleName());
            }else if (id == R.id.what_coffee){
                changeTabFragment(WhatCoffeeFragment.getInstance());
            }
        }
    };

    public void changeNavigationColor(int layoutID) {

        if (layoutID == R.id.home){
            setMainIndexMessageUnreadVisibility(true);
            changeHomeColor(true);
            changeOrderColor(false);
            changeStoreColor(false);
            changeMemberColor(false);
        }else if (layoutID == R.id.order){
            setMainIndexMessageUnreadVisibility(false);
            changeHomeColor(false);
            changeOrderColor(true);
            changeStoreColor(false);
            changeMemberColor(false);
        }else if (layoutID == R.id.purchase){
            setMainIndexMessageUnreadVisibility(false);
            changeHomeColor(false);
            changeOrderColor(false);
            changeStoreColor(true);
            changeMemberColor(false);
        }else if (layoutID == R.id.member){
            setMainIndexMessageUnreadVisibility(false);
            changeHomeColor(false);
            changeOrderColor(false);
            changeStoreColor(false);
            changeMemberColor(true);
        }
    }

    private void changeHomeColor(boolean isClicked) {
        if (isClicked) {
            imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_fill));
            txtHome.setTextColor(getResources().getColor(R.color.white));
        } else {
            imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_line));
            txtHome.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void changeOrderColor(boolean isClicked) {
        if (isClicked) {
            imgOrder.setImageDrawable(getResources().getDrawable(R.drawable.bag_fill));
            txtOrder.setTextColor(getResources().getColor(R.color.white));
        } else {
            imgOrder.setImageDrawable(getResources().getDrawable(R.drawable.bag_line));
            txtOrder.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void changeStoreColor(boolean isClicked) {
        if (isClicked) {
            imgPurchase.setImageDrawable(getResources().getDrawable(R.drawable.cart_fill));
            txtPurchase.setTextColor(getResources().getColor(R.color.white));
        } else {
            imgPurchase.setImageDrawable(getResources().getDrawable(R.drawable.cart_line));
            txtPurchase.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void changeMemberColor(boolean isClicked) {
        if (isClicked) {
            imgMember.setImageDrawable(getResources().getDrawable(R.drawable.member_fill));
            txtMember.setTextColor(getResources().getColor(R.color.white));
        } else {
            imgMember.setImageDrawable(getResources().getDrawable(R.drawable.member_line));
            txtMember.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void initAnimation(Context context) {
        final Animation openAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_down);
        final Animation open2Animation = AnimationUtils.loadAnimation(context, R.anim.anim_down2);
        final Animation visibleAnimation = AnimationUtils.loadAnimation(context, R.anim.anime_visiable);

        final Animation upAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_up);

        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llOpen.setVisibility(View.VISIBLE);
                setMainIndexMessageUnreadVisibility(true);
                constClose.setVisibility(View.GONE);
                constBackground.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        upAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setMainIndexMessageUnreadVisibility(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llOpen.setVisibility(View.GONE);
                constClose.setVisibility(View.VISIBLE);
                constBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        constClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.const_title_close).startAnimation(openAnimation);
                findViewById(R.id.const_background).startAnimation(open2Animation);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setConstBase(false);
                    }
                }, 100);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        llOpen.setVisibility(View.VISIBLE);
                        llItemButton.startAnimation(visibleAnimation);
                    }
                }, 200);
            }
        });

        llOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConstBase(true);
                llOpen.startAnimation(upAnimation);
            }
        });
    }

    public void member(View view) {
        Toast.makeText(this, "member", Toast.LENGTH_SHORT).show();
    }

    public void message(View view) {
        Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
    }

    public void lottery(View view) {
        Toast.makeText(this, "lottery", Toast.LENGTH_SHORT).show();
    }

    public void donate(View view) {
        Toast.makeText(this, "donate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_MAIN_INDEX:
                changeTabFragment(MainIndexFragment.getInstance());
                break;
            case REQUEST_MEMBER_CARD:
                changeTabFragment(MemberCardFragment.getInstance());
                break;
            case REQUEST_MEMBER_CENTER:
                changeTabFragment(MemberCenterFragment.getInstance());
                break;
            case REQUEST_COUPON:
                addFragment(WebViewFragment.getInstance(R.string.coupon, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_COUPONS_URL));
                break;
            case REQUEST_POINT:
                addFragment(WebViewFragment.getInstance(R.string.my_point, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_SMILEPOINT_URL));
                break;
            case REQUEST_MAIL:
                addFragment(MailFileFragment.getInstance());
                break;
            case REQUEST_MESSAGE:
                addFragment(MessageFragment.getInstance());
                break;
            case REQUEST_SHOPPING_CART:
                changeTabFragment(ShoppingCartFragment.getInstance());
                break;
            case REQUEST_BUSINESS:
                addFragment(BusinessFragment.getInstance());
                break;
            case REQUEST_LOT_LIST:
                addFragment(DrawLotsFragment.getInstance());
                break;
            case REQUEST_DONATE:
                DonateFragment.getInstance().type_idMode("0");
                addFragment(DonateFragment.getInstance());
                break;
            case REQUEST_MESSAGE_LIST:
                if(mainPresenter.getShopkeeper().equals("Y")){
                    addFragment(AdminMessageFragment.getInstance());
                }else{
                    addFragment(MessageListFragment.getInstance(mainPresenter.getUserID(), mainPresenter.getMobile(), "N"));
                }
                break;
        }
    }

    @Override
    public void refreshBadge() {
        //mainPresenter.getMailBadgeFromApi();
        //mainPresenter.getMessageBadgeFromApi();
        mainPresenter.getBadgeNumberFromApi();

        //setAppBadge();
    }

    @Override
    public void setMailBadge(String count) {
        appToolbar.setMailBadgeCount(Integer.parseInt(count));
    }

    @Override
    public void setMessageBadge(String count) {
        appToolbar.setMessageBadgeCount(Integer.parseInt(count));
    }

    // 判斷畫面上顯示的是否為MainIndexFragment
    public boolean isMainIndex() {
        boolean isMainIndex = false;
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        switch (fragmentList.size()) {
            case 0:
                isMainIndex = false;
                break;
            case 1:
                if (getSupportFragmentManager().getFragments().get(0).equals(MainIndexFragment.getInstance())) {
                    isMainIndex = true;
                }
                break;
            case 2:
                for (Fragment f : fragmentList) {
                    if (f instanceof MainIndexFragment) {
                        isMainIndex = true;
                        break;
                    }
                }
                break;
            default:
                isMainIndex = false;
        }
        return isMainIndex;
    }

    @Override
    public void setAllBadge(String count) {
        // messageUnreadNum_pushUnreadNum_cartTotalQuantity
        String[] array = count.split("_");

        //  客服留言未讀
        if(array.length > 0){
            EOrderApplication.messageBadgeCount = array[0];
            appToolbar.setMessageBadgeCount(Integer.parseInt(array[0]));
        }else{
            appToolbar.setMessageBadgeCount(0);
        }

        // 訊息夾未讀
        if (array.length > 1) {
            EOrderApplication.mailBadgeCount = array[1];
            appToolbar.setMailBadgeCount(Integer.parseInt(array[1]));
            if(isMainIndex()){
                setMainIndexMessageUnreadVisibility(true);
            }
        }else{
            appToolbar.setMailBadgeCount(0);
        }

        // 購物車商品數量
        if (array.length == 4) {
            EOrderApplication.cartBadgeCount = array[3];
            if (array[3].equals("0")) {
                tvShoppingCart.setVisibility(View.GONE);
            } else {
                tvShoppingCart.setVisibility(View.VISIBLE);
                tvShoppingCart.setText(array[3]);
            }
            /*
            if (array[2].equals("0")) {
                tvShoppingCartETicket.setVisibility(View.GONE);
            } else {
                tvShoppingCartETicket.setVisibility(View.VISIBLE);
                tvShoppingCartETicket.setText(array[2]);
            }
            */
        }
        // 購物車沒東西時未讀數量會收到空字串
        else {
            tvShoppingCart.setVisibility(View.GONE);
            //tvShoppingCartETicket.setVisibility(View.GONE);
        }
    }

    public void checkLoginForLot() {
        mainPresenter.checkLoginForLot();
    }

    public void setTopBarVisibility(boolean isVisible) {
        if (isVisible) { //回到MainIndexFragment
            llOpen.setVisibility(View.VISIBLE); //開起來的選單
            llItemButton.setVisibility(View.VISIBLE);
            constBase.setVisibility(View.VISIBLE);
            constBackground.setVisibility(View.VISIBLE);
            constClose.setVisibility(View.GONE);
            setConstBase(false);
        } else {
            llOpen.setVisibility(View.GONE);
            llItemButton.setVisibility(View.GONE);
            constBase.setVisibility(View.GONE);
            constBackground.setVisibility(View.GONE);
            constClose.setVisibility(View.GONE);
        }
    }

    public void setConstBase(boolean isOpen) {
        ViewGroup.LayoutParams params = constBase.getLayoutParams();
        if (isOpen) {
//            params.height = 150;
            params.height = 50;
        } else {
//            params.height = 100;
            params.height = 30;
        }
        constBase.setLayoutParams(params);
    }

    @Override
    public void intentToLogin(int requestCode) {
        IntentUtils.intentToLogin(this, requestCode);
    }

    public void intentToVerifyCode() { IntentUtils.intentToVerifyCode(this, true); }

    public CustomBottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    private void removeAllStackFragment() {

        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }

        Message message = new Message();
        message.what = BACK_STACK_CLEAR;
        handler.sendMessage(message);
    }

    private void removeExistFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Message message = new Message();
        message.what = FRAGMENT_REMOVE;
        handler.sendMessage(message);
    }

    @Override
    public void changeTabFragment(BaseFragment willChangeFragment) {
        this.willChangeFragment = willChangeFragment;
        Log.e(TAG, "changeTabFragment" + willChangeFragment);
        removeAllStackFragment();
        addFragment(willChangeFragment);
    }

    @Override
    public void addFragment(BaseFragment baseFragment) {
        this.willChangeFragment = baseFragment;
        Fragment fragment = getSupportFragmentManager().findFragmentById(baseFragment.getId());
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitNow();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame, baseFragment, "");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void bindWebView(WebView webView) {
        this.webView = webView;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                result.cancel();
                return true;
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                if(currentFragment instanceof ProductDetailFragment){

                }else {
                    if (title.indexOf("html") == -1 && title.indexOf("com") == -1) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setAppTitleString(title);
                            }
                        }, 1000);
                    }
                }
            }
        });
        // 關閉調試模式以提高性能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(false);
        }
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 禁用滾動條
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // WebView启用混合内容
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setUseWideViewPort(true);
        webView.addJavascriptInterface(new AndroidJsInterface(), "hamels");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setBackgroundColor(getResources().getColor(R.color.gray));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                try {
                    if (url.startsWith("https://maps.app.goo.gl/") || url.startsWith("https://www.google.com.tw/")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } else if (url.startsWith("http:") || url.startsWith("https:")) {
                        return false;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    return false;
                }
//            webView.loadUrl(url);
                return true;
            }
        });
    }

    public void setCustomerData() {
        mainPresenter.setCustomerData();
    }

    public class AndroidJsInterface {

        @JavascriptInterface
        public String jsCall_getVariable(String Info) {
            String sData = "";
            try {
                switch (Info){
                    case "customer_id":
                        sData = EOrderApplication.CUSTOMER_ID;
                        break;
                    case "connection_name":
                        sData = EOrderApplication.dbConnectName;
                        break;
                    default:
                        JSONObject oMemberData = new JSONObject(getUser().toString());
                        sData = oMemberData.getString(Info);
                        break;
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return sData;
        }

        @JavascriptInterface
        public void jsCall_goLoginPage(String page, String function) {
            goPage();
        }


        @JavascriptInterface
        public void jsCall_goOrderPage(String orderType) {
            goOrderPage(orderType, "", "");
        }

        @JavascriptInterface
        public void jsCall_showCustomerService(String page) {
            if(page.equals("")) {
                //showErrorAlert("登入後才可使用");
                EOrderApplication.MESSAGE_TAG = "coffee";
                mainPresenter.checkLoginForMessageList();
            }else{
                //  開啟外部網址
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(page));
                startActivity(intent);
            }
        }

        @JavascriptInterface
        public void jsCall_goShopPage(String salesType) {
            String isETicket = salesType.equals("E") ? "Y" : "N";
            goProductPage(isETicket);
        }
        @JavascriptInterface
        public void jsCall_goHomePage() {
            changeTabFragment(MainIndexFragment.getInstance());
        }
        @JavascriptInterface
        public void jsCall_setShopCartNumToApp() {
            refreshBadge();
        }

        @JavascriptInterface
        public void jsCall_goShoppingCart(String orderType) {
            mainPresenter.checkLoginForShoppingCart(orderType);
        }

        @JavascriptInterface
        public void jsCall_SavePaySchemeOrderData(String sParam){
            savePaySchemeOrderData(sParam);
        }

        @JavascriptInterface
        public void jsCall_PaySchemeGoOrderDetail(String sParam){
            savePaySchemeOrderData(sParam);
            String[] oData = sParam.split("\\|");

            Uri data = Uri.parse("maverickfood://com.hamels.huanan?order_type=" + oData[0] + " + order_id=" + oData[1] + "&meal_no=" + oData[2]);
            Intent intent = new Intent(Intent.ACTION_VIEW, data);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, RESULT_OK);
        }

        @JavascriptInterface
        public void jsCall_setShoppingCartAppTitle(String sParam) {
            ShoppingCartFragment.getInstance().setShoppingCartAppTitle(sParam);
        }
        @JavascriptInterface
        public void jsCall_SharedPDF(String sPDFUrl) {
            new DownloadFile().execute(sPDFUrl, "Shared");
        }
        @JavascriptInterface
        public void jsCall_DownloadPDF(String sPDFUrl) {
            new DownloadFile().execute(sPDFUrl, "Download");
        }
        @JavascriptInterface
        public void jsCall_copyToClipboard(String sAccount) {
            ClipboardManager clipboard = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", sAccount);

            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getBaseContext(), "已複製", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePaySchemeOrderData(String sParam){
        getRepositoryManager(this).savePaySchemeOrderData(sParam);
    }

    public void detachWebView() {
        webView = null;
    }

    public void setTabPage(int page) {
        MenuItem menuItem = bottomNavigationView.getMenu().getItem(page);
        bottomNavigationView.setIconScaleType(menuItem);
    }

    public void resetPassword() {
        setTabPage(0);
        mainPresenter.logout();
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            String currentPage = "";
            String goBackPage = "";

            WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                currentPage = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()).getUrl();
                goBackPage = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
            }
            if (currentPage.indexOf("orderDetail.html") > 0
                    || goBackPage.indexOf("order.html") > 0
                    || currentPage.indexOf("pay_complete.html") > 0) {
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDER_URL + "?orderType=" + "");
            } else if (currentPage.indexOf("order.html") > 0) {
                changeTabFragment(MemberCenterFragment.getInstance());
                webView = null;
            } else if(currentPage.indexOf("ebook.html") > 0){
                changeTabFragment(MainIndexFragment.getInstance());
            } else if(currentPage.indexOf("shoppingcart_list_product.html") > 0){
                mainPresenter.GetShopCartLocationQuantity();
                //changeTabFragment(ShoppingCartFragment.getInstance());
            } else if(currentPage.indexOf("AioCheckOut") > 0){
                webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_PAY_COMPLETE_URL + "?isSuccess=false" + "");
            } else {
                webView.goBack();
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // 首頁才顯示回上一頁
                if (isMainIndex()) {
                    new AlertDialog.Builder(this).setTitle(null).setMessage(R.string.close_hint)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                } else {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                    if(currentFragment instanceof MessageListFragment) {   //  商品內頁
                        MessageListBack("1");
                    }else {
                        // 其他就顯示首頁
                        changeNavigationColor(R.id.home);
                        changeTabFragment(MainIndexFragment.getInstance());
                    }
                }

//                boolean isAlert= false;
//                for(Fragment f: getSupportFragmentManager().getFragments()){
//                    // 首頁才顯示回上一頁
//                    if(f.equals(MainIndexFragment.getInstance())){
//                        new AlertDialog.Builder(this).setTitle(null).setMessage(R.string.close_hint)
//                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        System.exit(0);
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.no, null)
//                                .show();
//                        isAlert= true;
//                        break;
//                    }
//                }
//                // 其他就顯示首頁
//                if(!isAlert){
//                    changeNavigationColor(R.id.home);
//                    changeTabFragment(MainIndexFragment.getInstance());
//                }
            } else {
                // 點推播通知進來的
                if (getSupportFragmentManager().getBackStackEntryCount() == 1
                        && ( getSupportFragmentManager().getFragments().size() == 0
                            || getSupportFragmentManager().getFragments().get(0).equals(MailFileFragment.getInstance()))
                    ) {
                    changeNavigationColor(R.id.home);
                    changeTabFragment(MainIndexFragment.getInstance());
                } else {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame);

                    if(currentFragment instanceof MainIndexFragment) {
                        new AlertDialog.Builder(this).setTitle(null).setMessage(R.string.close_hint)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.exit(0);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .show();
                    }else if(currentFragment instanceof ProductDetailFragment){
                        addFragment(ProductFragment.getInstance());
                    }else if(currentFragment instanceof ProductFragment){
                        addFragment(ProductMainTypeFragment.getInstance());
                    }else if(currentFragment instanceof LocationDescFragment){
                        addFragment(LocationFragment.getInstance(""));
                    }else if(currentFragment instanceof MemberCenterFragment){
                        addFragment(MainIndexFragment.getInstance());
                    }else if(currentFragment instanceof ProductMainTypeFragment){
                        mainPresenter.getLocationList();
                    }else if(currentFragment instanceof LocationFragment || currentFragment instanceof WhatCoffeeFragment){
                        addFragment(MainIndexFragment.getInstance());
                    }else if(currentFragment instanceof MessageListFragment){
                        MessageListBack("2");
                    }else if(currentFragment instanceof MemberInfoChangeFragment
                    || currentFragment instanceof PasswordChangeFragment
                    || currentFragment instanceof MemberPointFragment
                    || currentFragment instanceof AboutFragment
                    || currentFragment instanceof TransRecordFragment){
                        addFragment(MemberCenterFragment.getInstance());
                    }else{
                        super.onBackPressed();
                    }
                }
            }
        }
    }
    public void MessageListBack(String sType){
        if(EOrderApplication.MESSAGE_TAG.equals("coffee")){
            addFragment(WhatCoffeeFragment.getInstance());
        }else{
            if(sType.equals("1")){
                // 其他就顯示首頁
                changeNavigationColor(R.id.home);
                changeTabFragment(MainIndexFragment.getInstance());
            }else {
                super.onBackPressed();
//                if(!getSupportFragmentManager().isStateSaved()){
//                    changeNavigationColor(R.id.home);
//                    changeTabFragment(MainIndexFragment.getInstance());
//                }else {
//                    super.onBackPressed();
//                }
            }
        }
    }

    public void ShoppingBackPage(String LocationQuantity){
        int iLocationQuantity = Integer.parseInt(LocationQuantity);
        if(iLocationQuantity < 2){
            changeTabFragment(MainIndexFragment.getInstance());
        }else{
            changeTabFragment(ShoppingCartFragment.getInstance());
        }
    }

    public void ProductLocationFragment(int location_count){
        if(location_count == 1 || location_count == 0){
            changeTabFragment(MainIndexFragment.getInstance());
        }else{
            changeTabFragment(LocationFragment.getInstance(""));
        }
    }

    public void goMemberCard() {
        mainPresenter.checkLoginForMemberCard();
    }

    public void checkLoginForCoupon() {
        mainPresenter.checkLoginForCoupon();
    }

    public void checkLoginForBusiness() {
        mainPresenter.checkLoginForBusiness();
    }

    public void setShoppingCartCount(String Count) {
        if (!Count.equals("")) {
            tvShoppingCart.setVisibility(View.VISIBLE);
            tvShoppingCart.setText(Count);
        } else {
            tvShoppingCart.setVisibility(View.GONE);
        }
    }

    public void setMainIndexMessageUnreadVisibility(boolean isVisible) {
        if (isVisible && (!tvMessageUnread.getText().toString().equals("") && !tvMessageUnread.getText().toString().equals("0"))) {
            tvMessageUnread.setVisibility(View.VISIBLE);
        } else {
            tvMessageUnread.setVisibility(View.GONE);
        }
    }

    public void goNewsDetail(String news_id) {
        mainPresenter.goNewsDetail(news_id);
    }

    public User getUser() {
        return mainPresenter.JsCallGetMemberInfo();
    }

    public void goPage() {
        mainPresenter.checkLoginForMemberCenter();
    }

//    public void goMessagePage() {
//        mainPresenter.checkLoginForMessage();
//    }

    public void goOrderPage(String orderType, String sOrderID, String sMealNo) {
        runOnUiThread(new Runnable() {
            public void run() {
                changeNavigationColor(R.id.member); //會員中心
            }
        });
        changeTabFragment(TransRecordFragment.getInstance(orderType, sOrderID, sMealNo));
    }

    public void goProductPage(final String isETicket) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (isETicket.equals("Y")) {
                    changeNavigationColor(R.id.purchase); // 買提貨券
                } else {
                    changeNavigationColor(R.id.order); //我要點餐
                }
            }
        });

        checkMerchantCount("PRODUCT", isETicket);
    }

    // 商店清單: 當商店數量小於一時，直接顯示商品清單
    public void checkMerchantCount(final String type, final String isETicket) {

        if (isETicket.equals("Y")) {
            //  買提貨卷
            mainPresenter.saveFragmentMainType("", isETicket);
            changeTabFragment(ProductMainTypeFragment.getInstance());
        } else {
            //  外帶外送
            mainPresenter.saveFragmentLocation("");
            addFragment(LocationFragment.getInstance("Menu"));
        }


//        BasePresenter basePresenter = new BasePresenter(this, getRepositoryManager(this));

//        MemberRepository.getInstance().getMerchantList(sCustomerID, new ApiCallback<BaseModel<List<Merchant>>>(basePresenter) {
//            @Override
//            public void onApiSuccess(BaseModel<List<Merchant>> response) {
//                super.onApiSuccess(response);
//                List<Merchant> merchantList = response.getItems();
//                if (merchantList != null) {
//                    switch (type) {
//                        case "PRODUCT":
//                            if(isETicket.equals("Y")){
//                                changeTabFragment(ProductMainTypeFragment.getInstance(0, isETicket));
//                            }else{
//                                if (merchantList.size() == 1) {
//                                    //int merchantID = merchantList.get(0).getId();
//                                    changeTabFragment(ProductMainTypeFragment.getInstance(merchantList.get(0).getId(), isETicket));
//                                } else {
//                                    changeTabFragment(ProductMerchantFragment.getInstance("PRODUCT", isETicket));
//                                }
//                            }
//                            break;
//                        case "STORE":
//                            if (merchantList.size() == 1) {
//                                addFragment(LocationFragment.getInstance(merchantList.get(0).getId()));
//                            } else {
//                                addFragment(ProductMerchantFragment.getInstance("STORE", isETicket));
//                            }
//                            break;
//                    }
//                }
//            }
//        });
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(willChangeFragment.getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    public void changeAppBrightness(int brightness) {
        Window window = MainActivity.this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);

        // 恢复屏幕亮度
        getWindow().getAttributes().screenBrightness = originalBrightness;
        getWindow().setAttributes(getWindow().getAttributes());
    }

    public void getPermissionsCamera(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
    }

    public void showErrorAlert(String message) {
        if(!MainActivity.this.isFinishing()){
            new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        }
    }

    public void initSelectCustomer(String sMode) { IntentUtils.intentToCustomer(this, sMode); }

    public void getCheckCustomerNo(String sCutomerNo){
        mainPresenter.checkCustomerNo(sCutomerNo);
    }
    @Override
    public void setCustomer(Customer customers, String sCustomerID, String sCustomerName, String sApiUrl) {
        TextView tvEOrder = findViewById(R.id.tv_eorder);
        if(customers == null && (sCustomerID.equals("") || sCustomerName.equals(""))){
            //tvEOrder.setText("全部商家");
            EOrderApplication.CUSTOMER_ID = "";
            EOrderApplication.CUSTOMER_NAME = "";
            EOrderApplication.sApiUrl = "";
        }else if(!sCustomerName.equals((""))){
            //tvEOrder.setText(sCustomerName);
            EOrderApplication.CUSTOMER_ID = sCustomerID;
            EOrderApplication.CUSTOMER_NAME = sCustomerName;
            EOrderApplication.sApiUrl = sApiUrl;
        }else{
            //tvEOrder.setText(customers.getCustomerName());
            EOrderApplication.CUSTOMER_ID = customers.getCustomerID();
            EOrderApplication.CUSTOMER_NAME = customers.getCustomerName();
            EOrderApplication.sApiUrl = customers.getApiUrl();
        }
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();
    }

    public void getCustomer(){
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));
        mainPresenter.getCustomer();
    }

    public void ChkVision(){
        mainPresenter = new MainPresenter(this, getRepositoryManager(this));
        mainPresenter.getOnlineVision();
    }

    public void getVersion(String sOnlineVision) {

        if(compareVersion(VersionCode + "", sOnlineVision)){
            isUpdate = true;
        }
    }

    public void CreateWebSocket() {
        boolean isCreate = false;
        if((EOrderApplication.WEB_SOCKET_MOBILE_CHK != EOrderApplication.WEB_SOCKET_MOBILE) || webSocket == null){
            isCreate = true;
            EOrderApplication.WEB_SOCKET_MOBILE_CHK = EOrderApplication.WEB_SOCKET_MOBILE;
        }

        if (!EOrderApplication.WEB_SOCKET_PATH.equals("") && !EOrderApplication.WEB_SOCKET_MOBILE.equals("") && isCreate) {
            String sWssUrl = EOrderApplication.WEB_SOCKET_PATH.replace("https", "wss") + "?connector_id=" + EOrderApplication.WEB_SOCKET_MOBILE;
            Request request = new Request.Builder().url(sWssUrl).build();
            webSocket = client.newWebSocket(request, new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    Log.d(TAG, "WebSocket Connected");
                }

                @Override
                public void onMessage(WebSocket webSocket, String message) {
                    Log.d(TAG, "Message Received: " + message);
                    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                    if (jsonObject.has("function_name")) {
                        String functionName = jsonObject.get("function_name").getAsString();
                        if ("leave_message".equals(functionName)) {
                            EventBus.getDefault().post(new MessageEvent(message));
                        }
                    }
                }

                @Override
                public void onClosing(WebSocket webSocket, int code, String reason) {
                    Log.d(TAG, "WebSocket Closing: " + reason);
                    closeWebSocket();
                    EOrderApplication.WEB_SOCKET_MOBILE_CHK = "";
                    scheduleReconnect(); // 安排重連
                }

                @Override
                public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                    Log.d(TAG, "WebSocket Failure: " + t.getMessage());
                    closeWebSocket();
                    EOrderApplication.WEB_SOCKET_MOBILE_CHK = "";
                    scheduleReconnect(); // 安排重連
                }
            });
        }
    }

    private void scheduleReconnect() {
        // 延遲重連，避免過於頻繁的重連導致伺服器過載
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeWebSocket();
                CreateWebSocket();
            }
        }, 5000);
    }

    public void closeWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "Client closed");
            webSocket = null;
        }
    }

    public void sendMessageToWebSocket(String sMemberID) {
        if (webSocket != null) {
            JsonObject notification = new JsonObject();
            notification.addProperty("connection_name", EOrderApplication.dbConnectName);
            notification.addProperty("customer_id", EOrderApplication.CUSTOMER_ID);
            notification.addProperty("member_id", sMemberID);
            notification.addProperty("function_name", "leave_message");
            String notificationMessage = gson.toJson(notification);
            webSocket.send(notificationMessage);
            Log.d("WebSocket", "Sent: " + notificationMessage);
        }
    }

    private void AlertUpdateApp(){
        String PackageName = this.getPackageName();
        new AlertDialog.Builder(this).setTitle(R.string.dialog_hint).setMessage("發現新版本")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isUpdate = true;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + PackageName + "&hl=zh-TW"));
                        startActivity(intent);
                    }
                })
                .show();
    }

    /**
     * 版本号比较
     */
    public static boolean compareVersion(String sAppVersion, String sOnlineVersion) {

        if(sAppVersion == null || sOnlineVersion == null){
            return false;
        }

        if (sAppVersion.equals("") || sOnlineVersion.equals("")) {
            return false;
        }

        double AppVersion = Double.parseDouble(sAppVersion);
        double OnlineVersion = Double.parseDouble(sOnlineVersion);

        if(AppVersion >= OnlineVersion){
            return false;
        }else{
            return true;
        }
    }

    public void EditFragmentBottom(boolean showTop, boolean showBottom) {
        // 找到 FrameLayout 的引用
        FrameLayout frameLayout = findViewById(R.id.frame);
        // 取得 FrameLayout 的父容器 (ConstraintLayout) 的參數
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
        if(showTop){
            layoutParams.topMargin = (int) (50 * getResources().getDisplayMetrics().density);
        }else{
            layoutParams.topMargin = (int) (0 * getResources().getDisplayMetrics().density);
        }

        if(showBottom){
            layoutParams.bottomMargin = (int) (65 * getResources().getDisplayMetrics().density);
        }else{
            layoutParams.bottomMargin = (int) (0 * getResources().getDisplayMetrics().density);
        }

        // 更新 FrameLayout 的父容器的參數
        frameLayout.setLayoutParams(layoutParams);
    }

    public void OpenShared(){
        // 获取剪贴板管理器
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String copiedText = mainPresenter.getUserName() + " 邀請您下載 " + EOrderApplication.CUSTOMER_NAME + " APP，註冊時輸入邀請碼: " + mainPresenter.getInvitationCode() + "，即可獲得新會員獎勵";
        copiedText += "\niOS下載連結 : https://itunes.apple.com/app/id6458143369";
        copiedText += "\nAndroid下載連結 : https://play.google.com/store/apps/details?id=com.hamels.huanan";
        if (clipboard != null) {
            // 创建一个ClipData对象，将文本复制到剪贴板
            ClipData clip = ClipData.newPlainText("Copied Text", copiedText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "已複製", Toast.LENGTH_SHORT).show();
        }

        // 打开分享对话框
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, copiedText);
        startActivity(Intent.createChooser(shareIntent, "分享到"));

        // 恢复屏幕亮度
        getWindow().getAttributes().screenBrightness = originalBrightness;
        getWindow().setAttributes(getWindow().getAttributes());
    }

    private void createQRcodeImage(String content, ImageView qrcode_img) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置纠错级别为最高
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            Bitmap qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrBitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.Black) : getResources().getColor(R.color.white));
                }
            }

            qrcode_img.setImageBitmap(qrBitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    private void setScreenBrightnessToMax() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 1.0f; // 亮度值范围是 0.0 到 1.0
        getWindow().setAttributes(layoutParams);
    }

    private class DownloadFile extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean bResult = false;
            String fileUrl = strings[0];
            String Mode = strings[1];
            File pdffile = new File(fileUrl);
            String fileName = pdffile.getName();
            try {
                // 检查是否已经获得写入外部存储的权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有权限，请求权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                storageDir.mkdirs();
                sPDFDir = storageDir.getPath();
                File oldFile = new File(storageDir.getPath() + "/" + fileName);
                if (oldFile.exists()) {
                    // 如果文件存在，首先删除它
                    oldFile.delete();
                }
                File pdfFile = new File(storageDir, fileName);

                FileOutputStream output = new FileOutputStream(pdfFile);
                InputStream input = connection.getInputStream();

                byte[] buffer = new byte[1024];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }

                output.close();
                input.close();

                bResult = true;

                if(Mode.equals("Shared")) {
                    // Share the downloaded PDF using FileProvider
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("application/pdf");

                    Uri pdfUri = FileProvider.getUriForFile(getApplicationContext(), "com.hamels.daybydayegg.fileprovider", pdfFile);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);

                    // Grant temporary read permission to the content URI
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivity(Intent.createChooser(shareIntent, "分享到"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bResult;
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // 共享成功，显示成功消息
                Toast.makeText(getApplicationContext(), "文件已成功儲存至: " + sPDFDir, Toast.LENGTH_LONG).show();
            }
        }
    }
}
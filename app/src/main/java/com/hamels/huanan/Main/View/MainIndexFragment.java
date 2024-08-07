package com.hamels.huanan.Main.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Login.VIew.LoginActivity;
import com.hamels.huanan.Main.Contract.MainIndexContract;
import com.hamels.huanan.Main.Presenter.MainIndexPresenter;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Carousel;
import com.hamels.huanan.Utils.IntentUtils;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hamels.huanan.EOrderApplication.*;

public class MainIndexFragment extends BaseFragment implements MainIndexContract.View{
    public static final String TAG = MainIndexFragment.class.getSimpleName();

    private static MainIndexFragment fragment;
    private XBanner mXBanner;
    private ImageView imv_membercard;
//    private ImageView img_point , img_coupon;
    private ConstraintLayout layout_pay , layout_coupon , layout_gift
        , layout_enterprise;
    private TextView txt_name;
    private MainIndexContract.Presenter mainindexPresenter;
    public static MainIndexFragment getInstance() {
        if (fragment == null) {
            fragment = new MainIndexFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        mainindexPresenter = new MainIndexPresenter(this, getRepositoryManager(getContext()));

        ((MainActivity) getActivity()).setCustomerData();

        initView(view);
        return view;
    }
    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(false,false);
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_index);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(true);
        ((MainActivity) getActivity()).setAppToolbarVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(true);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        mXBanner = view.findViewById(R.id.xbanner);

//        // 獲取屏幕高度
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenHeight = displayMetrics.heightPixels - (int) (65 * getResources().getDisplayMetrics().density);
//
//        // 設置 mXBanner 的高度為屏幕高度
//        mXBanner.setLayoutParams(new ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.MATCH_PARENT,
//                screenHeight
//        ));

        imv_membercard = view.findViewById(R.id.img_member_card);
        imv_membercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goMemberCard();
            }
        });

        layout_pay = view.findViewById(R.id.layout_pay);
        layout_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        layout_coupon = view.findViewById(R.id.layout_coupon);
        layout_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).checkLoginForCoupon();
            }
        });
        layout_gift = view.findViewById(R.id.layout_gift);
        layout_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).checkLoginForLot();
            }
        });
        layout_enterprise = view.findViewById(R.id.layout_enterprise);
        layout_enterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).checkLoginForBusiness();
            }
        });

//        img_point = view.findViewById(R.id.img_point);
//        img_point.setOnClickListener(this);
//        img_coupon = view.findViewById(R.id.img_coupon);
//        img_coupon.setOnClickListener(this);


        txt_name = view.findViewById(R.id.txt_name);

        //mainindexPresenter.getCarouselList(CUSTOMER_ID);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_name.setText(mainindexPresenter.getName());

        if(mainindexPresenter.getGroup()!=null){
            if(mainindexPresenter.getGroup().equals("V")){
                imv_membercard.setBackgroundResource(R.drawable.membercard_vip);
            }else{
                imv_membercard.setBackgroundResource(R.drawable.membercard_normal);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mainindexPresenter.getCarouselList(CUSTOMER_ID);
    }

    public void getNoVerift() {
        //  尚未通過驗證，自動執行登出作業
        mainindexPresenter.logout();

//        new AlertDialog.Builder(fragment.getActivity())
//                .setTitle(R.string.dialog_hint)
//                .setMessage("尚未完成簡訊驗證")
//                .setPositiveButton(android.R.string.ok,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ((MainActivity) getActivity()).intentToVerifyCode();
//                            }
//                        })
//                .show();
    }

    public void CustomerOnlineISFalse() {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("此商家無開放APP購物").setPositiveButton(android.R.string.ok, null).show();
        ((MainActivity) requireActivity()).resetPassword();
    }

    @Override
    public void setMemberCardImg(String group) {
        if(group!=null){
            if(group.equals("V")){
                imv_membercard.setBackgroundResource(R.drawable.membercard_vip);
            }
        }
    }

    @Override
    public void setCarouselList(final List<Carousel> carouselList) {
        List<CustomViewsInfo> data = new ArrayList<>();
        String sDoMain = EOrderApplication.sApiUrl.equals("") ? ADMIN_DOMAIN : EOrderApplication.sApiUrl;
        for(int i = 0 ; i < carouselList.size();i++){
            data.add(new CustomViewsInfo(sDoMain + carouselList.get(i).getPicture_url(),carouselList.get(i).getId()));
        }
        mXBanner.setBannerData(R.layout.layout_main_activity_center_crop, data);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                // 獲取屏幕的高度
                int screenHeight = getResources().getDisplayMetrics().heightPixels - (int) (65 * getResources().getDisplayMetrics().density);

                ImageView img_carousel = (ImageView) view.findViewById(R.id.img_carousel);
                img_carousel.setAdjustViewBounds(false);
                img_carousel.setScaleType(ImageView.ScaleType.FIT_XY);

                // 創建ImageView的佈局參數
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, screenHeight);

                // 設置ImageView的佈局參數
                img_carousel.setLayoutParams(params);

                Glide
                    .with(getActivity())
                        .load(((CustomViewsInfo) model).getXBannerUrl())
                        // .load(R.drawable.e_order)
                    .into(img_carousel);
            }
        });
        mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Log.e(TAG,carouselList.get(position).getTitle());
                ((MainActivity) getActivity()).addFragment(NewsFragment.getInstance(carouselList.get(position)));
                //  ((MainActivity) getActivity()).addFragment(NewsFragment.getInstance(((CustomViewsInfo)model).getXBannerTitle()));
                //  ((MainActivity) getActivity()).goNewsDetail(((CustomViewsInfo)model).getXBannerTitle());
            }
        });

        mainindexPresenter.checkMemberData();

        CallActive();
    }

    public void CallActive(){
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).refreshBadge();
            EOrderApplication.WEB_SOCKET_MOBILE = mainindexPresenter.getMobile();
            ((MainActivity) activity).CreateWebSocket();
        }
    }
}


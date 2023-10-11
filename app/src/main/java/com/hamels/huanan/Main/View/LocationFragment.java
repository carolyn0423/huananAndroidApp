package com.hamels.huanan.Main.View;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Login.VIew.LoginActivity;
import com.hamels.huanan.Main.Adapter.LocationListAdapter;
import com.hamels.huanan.Main.Contract.LocationListContract;
import com.hamels.huanan.Main.Presenter.LocationListPresenter;
import com.hamels.huanan.Product.View.ProductMainTypeFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.ApiRepository.ApiRepository;
import com.hamels.huanan.Repository.ApiRepository.MemberRepository;
import com.hamels.huanan.Repository.Model.Store;
import com.hamels.huanan.Utils.IntentUtils;

import java.util.List;
import java.util.UUID;

import static com.hamels.huanan.Main.Presenter.LocationListPresenter.FUNCTIONNAME_1;
import static com.hamels.huanan.Main.Presenter.LocationListPresenter.FUNCTIONNAME_2;
import static com.hamels.huanan.Main.Presenter.LocationListPresenter.FUNCTIONNAME_3;

public class LocationFragment extends BaseFragment implements LocationListContract.View {
    public static final String TAG = LocationFragment.class.getSimpleName();

    private static LocationFragment fragment;
    private TextView btn_functionname_1, btn_functionname_2, btn_functionname_3;
    private RecyclerView recyclerView;
    private Group noLocationGroup;
    private TextView tvGoHome;

    private LocationListAdapter locationListAdapter;
    private LocationListContract.Presenter storeListPresenter;

    private String location_id = "";
    private int location_count = 0;
    private static final int REQUEST_LOCATION_PERMISSION = 100;

    public static LocationFragment getInstance() {
        if (fragment == null) {
            fragment = new LocationFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initView(view);

        return view;
    }


    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_shop);
        ((MainActivity) getActivity()).refreshBadge();

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
//        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        noLocationGroup = view.findViewById(R.id.no_location_group);

        //  清除API 暫存, 重新取得URL
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();

        storeListPresenter = new LocationListPresenter(this, getRepositoryManager(getContext()));
        locationListAdapter = new LocationListAdapter(this, storeListPresenter);

        location_id = storeListPresenter.getFragmentLocation();

        btn_functionname_1 = view.findViewById(R.id.btn_functionname_1);
        btn_functionname_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storeListPresenter.getUserLogin()) {
                    storeListPresenter.setFunctionname(FUNCTIONNAME_1, location_id);
                }else{
                    new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("此功能需登入，您要登入嗎?")
                            .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
                                    fragment.getActivity().startActivityForResult(intent, LocationFragment.REQUEST_LOCATION_PERMISSION);

                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            }).show();
                }
            }
        });

        btn_functionname_2 = view.findViewById(R.id.btn_functionname_2);
        btn_functionname_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeListPresenter.setFunctionname(FUNCTIONNAME_2, location_id);
            }
        });

        btn_functionname_3 = view.findViewById(R.id.btn_functionname_3);
        btn_functionname_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeListPresenter.setFunctionname(FUNCTIONNAME_3, location_id);
            }
        });

        tvGoHome = view.findViewById(R.id.tv_go_home);
        tvGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeTabFragment(MainIndexFragment.getInstance());
            }
        });

        requestUserLocation();

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(locationListAdapter);

        ConstraintLayout.LayoutParams clFunctionname_1 = (ConstraintLayout.LayoutParams) btn_functionname_1.getLayoutParams();
        ConstraintLayout.LayoutParams clFunctionname_2 = (ConstraintLayout.LayoutParams) btn_functionname_2.getLayoutParams();
        ConstraintLayout.LayoutParams clFunctionname_3 = (ConstraintLayout.LayoutParams) btn_functionname_3.getLayoutParams();

        storeListPresenter.saveSourceActive("");
        storeListPresenter.setFunctionname(FUNCTIONNAME_3, location_id);

        if(!storeListPresenter.getUserLogin()) {
            btn_functionname_1.setVisibility(View.GONE);
            btn_functionname_2.setVisibility(View.GONE);

//            clFunctionname_2.endToStart = R.id.guideline_mid;
//            clFunctionname_2.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

            clFunctionname_3.startToEnd = R.id.guideline_right;
            clFunctionname_3.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        }else{
            btn_functionname_1.setVisibility(View.VISIBLE);
            btn_functionname_2.setVisibility(View.GONE);

//            clFunctionname_2.endToStart = clFunctionname_2.UNSET;
//            clFunctionname_2.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());

            clFunctionname_1.endToStart = R.id.guideline_mid;
            clFunctionname_1.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

            clFunctionname_3.startToEnd = R.id.btn_functionname_1;
            clFunctionname_3.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        }
    }

    public void changeToNewFunctionName(String functionname) {
        //Log.e(TAG, "changeToNewFunctionName : " + functionname);

        btn_functionname_1.setBackgroundResource(functionname == FUNCTIONNAME_1 ? R.drawable.bg_bluegreen_gradient_bar : R.drawable.bg_bluegreen_gradient_borderbar);
        btn_functionname_1.setTextColor(functionname == FUNCTIONNAME_1 ? Color.parseColor("#ffffff") : Color.parseColor("#808080"));

        btn_functionname_2.setBackgroundResource(functionname == FUNCTIONNAME_2 ? R.drawable.bg_bluegreen_gradient_bar : R.drawable.bg_bluegreen_gradient_borderbar);
        btn_functionname_2.setTextColor(functionname == FUNCTIONNAME_2 ? Color.parseColor("#ffffff") : Color.parseColor("#808080"));

        btn_functionname_3.setBackgroundResource(functionname == FUNCTIONNAME_3 ? R.drawable.bg_bluegreen_gradient_bar : R.drawable.bg_bluegreen_gradient_borderbar);
        btn_functionname_3.setTextColor(functionname == FUNCTIONNAME_3 ? Color.parseColor("#ffffff") : Color.parseColor("#808080"));
    }

    @Override
    public void setLocationList(List<Store> stores) {
        for (Store store : stores) {
            Log.e(TAG, store.getName());
        }

        if(location_count == 1){
            //  門市僅有一個，直接Go
            storeListPresenter.saveFragmentMainType(location_id, "N");
            ((MainActivity) getActivity()).addFragment(ProductMainTypeFragment.getInstance());
        }else {
            locationListAdapter.setData(stores);
        }
    }

    public void goProductMainType(String sLocationID) {
        storeListPresenter.saveFragmentMainType(sLocationID, "N");
        ((MainActivity) getActivity()).addFragment(ProductMainTypeFragment.getInstance());
    }
    public void goLocationDesc(Store store) {
        ((MainActivity) getActivity()).addFragment(LocationDescFragment.getInstance(store));
    }
    @Override
    public void intentToGoogleMap(String address) {
        IntentUtils.intentToGoogleMap((BaseActivity) getActivity(), address);
    }

    @Override
    public void intentToPhoneCall(String phone) {
        IntentUtils.intentToCall((BaseActivity) getActivity(), phone);
    }

    public void setOnlineShoppingFlag(boolean isFlag, List<Store> stores){
        if(stores.size() > 0) {
            location_count = stores.size();
            location_id = stores.get(0).getLocationID();
        }

        if (isFlag) {
            noLocationGroup.setVisibility(getView().GONE);
        } else {
            noLocationGroup.setVisibility(getView().VISIBLE);
        }
    }

    public void requestUserLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 在這裡處理位置更新
                EOrderApplication.lat = location.getLatitude();  // 獲取緯度
                EOrderApplication.lon = location.getLongitude();  // 獲取經度
            }

            @Override
            public void onProviderDisabled(String provider) {
                // 在這裡處理提供程序停用事件
            }

            @Override
            public void onProviderEnabled(String provider) {
                // 在這裡處理提供程序啟用事件
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // 在這裡處理狀態更改事件
            }
        };

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // 沒有權限，需要向用戶請求權限
            // 取得 GPS 權限
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Ask for permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
                return;
            }
        } else {
            // 已經有權限，可以繼續操作
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // 請求權限
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
}

package com.hamels.huanan.Main.View;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Main.Adapter.LocationListAdapter;
import com.hamels.huanan.Main.Contract.LocationListContract;
import com.hamels.huanan.Main.Presenter.LocationListPresenter;
import com.hamels.huanan.Product.View.ProductMainTypeFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.ApiRepository.ApiRepository;
import com.hamels.huanan.Repository.ApiRepository.MemberRepository;
import com.hamels.huanan.Repository.Model.ProductMainType;
import com.hamels.huanan.Repository.Model.Store;
import com.hamels.huanan.Utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends BaseFragment implements LocationListContract.View {
    public static final String TAG = LocationFragment.class.getSimpleName();

    private static LocationFragment fragment;
    private RecyclerView recyclerView;
    private Group noLocationGroup;

    private LocationListAdapter locationListAdapter;
    private LocationListContract.Presenter storeListPresenter;

    private ConstraintLayout clFragmentStore, clSearch;
    private TextInputLayout tlProductKeyword;
    private EditText etProductKeyword;
    private ImageView ivImgSearch;
    private TextView ivCancelText;


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

        // 添加点击监听器
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在整个Fragment被点击时执行操作
                getSearchKeyword();
            }
        });

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

        clFragmentStore = view.findViewById(R.id.fragment_store);

        noLocationGroup = view.findViewById(R.id.no_location_group);

        clSearch = view.findViewById(R.id.ct_search);

        tlProductKeyword = view.findViewById(R.id.tl_product_keyword);
        etProductKeyword = view.findViewById(R.id.et_product_keyword);

        ivImgSearch = view.findViewById(R.id.img_search);
        ivCancelText = view.findViewById(R.id.cancelText);

        //  清除API 暫存, 重新取得URL
        ApiRepository.repository = null;
        MemberRepository.memberRepository = null;
        ApiRepository.getInstance();
        MemberRepository.getInstance();

        storeListPresenter = new LocationListPresenter(this, getRepositoryManager(getContext()));
        locationListAdapter = new LocationListAdapter(this, storeListPresenter);

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(locationListAdapter);

        storeListPresenter.getLocationList("");

        tlProductKeyword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    getSearchKeyword();
                }
            }
        });

        tlProductKeyword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId , KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    getSearchKeyword();
                    return true;
                }
                return false;
            }
        });

        ivCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etProductKeyword.setText("");
                getSearchKeyword();
            }
        });

        ivImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchKeyword();
            }
        });
    }
    public void getSearchKeyword(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etProductKeyword.getWindowToken(), 0);
        storeListPresenter.getLocationList(etProductKeyword.getText().toString());
    }
    @Override
    public void setLocationList(List<Store> stores) {
        for (Store store : stores) {
            Log.e(TAG, store.getName());
        }

        List<Store> dataleft = new ArrayList<>();
        List<Store> dataright = new ArrayList<>();
        for(int i = 0 ; i < stores.size() ; i++){
            //  Log.e(TAG,stores.get(i).getLocationID());
            recyclerView.setBackgroundResource(R.color.white);
            if(i % 2 == 0){
                dataleft.add(stores.get(i));
            }
            else{
                dataright.add(stores.get(i));
            }
        }
        locationListAdapter.setData(dataleft ,dataright);
        recyclerView.scrollToPosition(0);
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
        if (isFlag) {
            noLocationGroup.setVisibility(getView().GONE);
        } else {
            noLocationGroup.setVisibility(getView().VISIBLE);
        }
    }
}

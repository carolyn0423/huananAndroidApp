package com.hamels.huanan.Main.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Base.BaseFragment;
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

public class LocationFragment extends BaseFragment implements LocationListContract.View {
    public static final String TAG = LocationFragment.class.getSimpleName();

    private static LocationFragment fragment;
    private RecyclerView recyclerView;
    private Group noLocationGroup;

    private LocationListAdapter locationListAdapter;
    private LocationListContract.Presenter storeListPresenter;
    private TextView tvFunctionname_1, tvFunctionname_2, tvFunctionname_3, tvViewBlack;

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

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(locationListAdapter);

        storeListPresenter.getLocationList();
    }
    @Override
    public void setLocationList(List<Store> stores) {
        for (Store store : stores) {
            Log.e(TAG, store.getName());
        }

        locationListAdapter.setData(stores);
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

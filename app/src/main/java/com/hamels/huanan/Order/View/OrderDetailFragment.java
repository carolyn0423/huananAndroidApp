package com.hamels.huanan.Order.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.R;

import java.util.Objects;

public class OrderDetailFragment extends BaseFragment {
    public static final String TAG = OrderDetailFragment.class.getSimpleName();

    private static OrderDetailFragment fragment;
    private WebView webView;

    private static final String ORDERID = "orderid";
    private static final String MEALNO = "meal_no";
    private String orderid = "";
    private String meal_no = "";

    public static OrderDetailFragment getInstance() {
        if (fragment == null) {
            fragment = new OrderDetailFragment();
        }

        return fragment;
    }

    public static OrderDetailFragment getInstance(String orderid, String meal_no) {
        Log.e(TAG, "----------------------------" + orderid);

        if (fragment == null) {
            fragment = new OrderDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(ORDERID, orderid);
        bundle.putString(MEALNO, meal_no);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);

        orderid = getArguments().getString(ORDERID, "");
        meal_no = getArguments().getString(MEALNO, "");

        initView(view);

        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.trans_record);
        ((MainActivity) getActivity()).refreshBadge();
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        //Toast.makeText(getActivity(), "order_id: " + orderid, Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "meal_no: " + meal_no, Toast.LENGTH_LONG).show();

        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_ORDERDETAIL_URL + "?order_id=" + orderid + "&meal_no=" + meal_no);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}

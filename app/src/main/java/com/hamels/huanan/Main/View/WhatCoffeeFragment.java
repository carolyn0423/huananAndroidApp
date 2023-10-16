package com.hamels.huanan.Main.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.R;

import java.util.Objects;

public class WhatCoffeeFragment extends BaseFragment {
    public static final String TAG = WhatCoffeeFragment.class.getSimpleName();

    private static WhatCoffeeFragment fragment;
    private WebView webView;

    public static WhatCoffeeFragment getInstance() {
        if (fragment == null) {
            fragment = new WhatCoffeeFragment();
        }

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).bindWebView(webView);

        // 使用WebViewClient監聽載入事件
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(((MainActivity) getActivity()) != null) {
                    ((MainActivity) getActivity()).setCartBadgeVisibility(false);
                }
            }
        });

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setGeolocationEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);

        webView.requestFocus();

        //webView.setScrollBarStyle(0);

        // 啟用滾動條
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);

        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_WHATCOFFEE_URL + "?mode=About&id=" + EOrderApplication.CUSTOMER_ID);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}

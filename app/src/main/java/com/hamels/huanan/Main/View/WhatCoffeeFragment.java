package com.hamels.huanan.Main.View;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.R;
import com.hamels.huanan.Widget.AppToolbar;

import java.util.Objects;

public class WhatCoffeeFragment extends BaseFragment {
    public static final String TAG = WhatCoffeeFragment.class.getSimpleName();

    private static WhatCoffeeFragment fragment;
    private WebView webView;
    protected AppToolbar appToolbar;

    public static WhatCoffeeFragment getInstance() {
        if (fragment == null) {
            fragment = new WhatCoffeeFragment();
        }

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_what_coffee, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        ((MainActivity) getActivity()).EditFragmentBottom(true, false);
        webView = view.findViewById(R.id.web_view);
        //((MainActivity) getActivity()).refreshBadge();
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(false); // 關閉調試模式以提高性能
        }

        ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).setCartBadgeVisibility(false);
            }
        }, 1000);

        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_WHATCOFFEE_URL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}

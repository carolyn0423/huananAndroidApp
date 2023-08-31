package com.hamels.huanan.Main.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

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
        View view = inflater.inflate(R.layout.fragment_what_coffee, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(false);
        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.title_what_coffee);
        ((MainActivity) getActivity()).refreshBadge();
        setBackButtonVisibility(true);
        setMailButtonVisibility(true);
        setMessageButtonVisibility(true);
        setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).bindWebView(webView);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_WHATCOFFEE_URL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}

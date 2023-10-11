package com.hamels.huanan.Main.View;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Store;

import java.util.Objects;

public class LocationDescFragment extends BaseFragment {
    public static final String TAG = LocationDescFragment.class.getSimpleName();

    private static LocationDescFragment fragment;
    private Store store;
    private WebView webView;

    public static LocationDescFragment getInstance(Store store) {
        if (fragment == null) {
            fragment = new LocationDescFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Store.TAG, store);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);
        if (getArguments() != null && getArguments().containsKey(Store.TAG)) {
            store = getArguments().getParcelable(Store.TAG);
        }
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (store != null) {
//            PicassoImageGetter imageGetter = new PicassoImageGetter(this.getContext(), tvContent);
//            Spannable html;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                html = (Spannable) Html.fromHtml(store.getDescription(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//            } else {
//                html = (Spannable) Html.fromHtml(store.getDescription(), imageGetter, null);
//            }
//
//            tvContent.setText(html);

            // urlWebView.loadUrl("https://www.google.com/"); //For URL

            //webView.getSettings().setJavaScriptEnabled(true);   //支持javascript
            //webView.setWebViewClient(new ArticleWebViewClient());
            //webView.loadData(store.getDescription(), "text/html", "UTF-8");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.setWebContentsDebuggingEnabled(false); // 關閉調試模式以提高性能
            }
            webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_CONTENT_URL + "?mode=Location&id=" + store.getLocationID());
        }
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        ((MainActivity) getActivity()).setAppTitleString(store.getName());

        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).bindWebView(webView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}

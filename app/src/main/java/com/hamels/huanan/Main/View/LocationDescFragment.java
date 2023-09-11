package com.hamels.huanan.Main.View;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Store;
import com.hamels.huanan.Utils.ArticleWebViewClient;
import com.hamels.huanan.Utils.PicassoImageGetter;

public class LocationDescFragment extends BaseFragment {
    public static final String TAG = LocationDescFragment.class.getSimpleName();

    private static LocationDescFragment fragment;
    private Store store;
    private ConstraintLayout layoutContent;
    private TextView tvContent;
    private WebView webView;
    Drawable drawable;

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
        View view = inflater.inflate(R.layout.fragment_location_desc, container, false);
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

            webView.getSettings().setJavaScriptEnabled(true);   //支持javascript
            webView.setWebViewClient(new ArticleWebViewClient());
            webView.loadData(store.getDescription(), "text/html", "UTF-8");
        }

        Resources res = this.getResources();
        drawable = res.getDrawable(R.drawable.bg_shadow_corner);
        layoutContent.setBackground(drawable);
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

        layoutContent = view.findViewById(R.id.layout_content);
        //tvContent = view.findViewById(R.id.tv_content);
        webView = view.findViewById(R.id.web_view);
    }
}

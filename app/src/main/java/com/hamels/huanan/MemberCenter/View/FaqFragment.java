package com.hamels.huanan.MemberCenter.View;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.MemberCenter.Contract.FaqContract;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Faq;

import java.util.Objects;

public class FaqFragment extends BaseFragment implements FaqContract.View{
    public static final String TAG = FaqFragment.class.getSimpleName();

    private static FaqFragment fragment;
    private String faq_id;
    private static final String FAQ_ID = "faq_id";
    private WebView webView;

    //private TextView tv_faq_data;

    public static FaqFragment getInstance(String faq_id) {
        if (fragment == null) {
            fragment = new FaqFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(FAQ_ID, faq_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);
        if (getArguments() != null) {
            faq_id = getArguments().getString(FAQ_ID, "");
            initView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        presenter = new FaqPresenter(this, getRepositoryManager(getContext()));
//        presenter.getFaqData(faq_id);
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        switch (faq_id){
            case "1":
                ((MainActivity) getActivity()).setAppTitle(R.string.privacy_policy);
                break;
            case "2":
                ((MainActivity) getActivity()).setAppTitle(R.string.terms_of_use);
                break;
        }

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        webView = view.findViewById(R.id.web_view);
        ((MainActivity) getActivity()).bindWebView(webView);

        //tv_faq_data = view.findViewById(R.id.tv_faq_data);
        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_CONTENT_URL + "?mode=Faq&id=" + faq_id);
    }

    @Override
    public void setFaqData(Faq faq) {
//        PicassoImageGetter imageGetter = new PicassoImageGetter(getContext(), tv_faq_data);
//        Spannable html;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            html = (Spannable) Html.fromHtml(faq.getAnswer(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//        } else {
//            html = (Spannable) Html.fromHtml(faq.getAnswer(), imageGetter, null);
//        }
//
//        tv_faq_data.setText(html);

        //tv_faq_data.setText(Html.fromHtml(faq.getAnswer()));
    }
    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) Objects.requireNonNull(getActivity())).detachWebView();
    }
}

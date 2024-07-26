package com.hamels.huanan.Main.View;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.huanan.Utils.ArticleWebViewClient;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.Contract.NewsContract;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Carousel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsFragment extends BaseFragment implements NewsContract.View {
    public static final String TAG = NewsFragment.class.getSimpleName();

    private static NewsFragment fragment;
    private static final String NEWS_ID = "news_id";
    private TextView  tv_news_title ,tv_news_content;
    private ImageView imageView;
    private Carousel carousel;
    private WebView webView;

    public static NewsFragment getInstance(Carousel carousel) {
        if (fragment == null) {
            fragment = new NewsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Carousel.TAG, carousel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        if (getArguments() != null && getArguments().containsKey(Carousel.TAG)) {
            carousel = getArguments().getParcelable(Carousel.TAG);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
//        webView = view.findViewById(R.id.web_view);
        setAppTitle(R.string.tab_news);
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

        imageView = view.findViewById(R.id.imageView);
        //tv_news_content = view.findViewById(R.id.tv_news_content);
        tv_news_title = view.findViewById(R.id.tv_news_title);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String sPictureUrl = carousel.getPicture_url2().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : carousel.getPicture_url2();
        Glide.with(getActivity()).load(EOrderApplication.sApiUrl + sPictureUrl).into(imageView);
        tv_news_title.setText(carousel.getTitle());

        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_CONTENT_URL + "?mode=News&id=" + carousel.getId());
        // 使用 HtmlCompat.fromHtml
//        CharSequence formattedText = HtmlCompat.fromHtml(carousel.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
//        tv_news_content.setText(formattedText);
//        String sContent = carousel.getContent();
//        webView.clearCache(true);
//        webView.getSettings().setJavaScriptEnabled(true);   //支持javascript
//        webView.setWebViewClient(new ArticleWebViewClient());
//        webView.loadData(sContent, "text/html", "UTF-8");
//        PicassoImageGetter imageGetter = new PicassoImageGetter(this.getContext(),tv_news_content);
//        Spannable html;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            html = (Spannable) Html.fromHtml(carousel.getContent(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//        } else {
//            html = (Spannable) Html.fromHtml(carousel.getContent(), imageGetter, null);
//        }
//
//        tv_news_content.setText(html);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) requireActivity()).detachWebView();
    }
}
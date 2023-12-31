package com.hamels.huanan.Login.VIew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.Main.View.MainIndexFragment;
import com.hamels.huanan.Main.View.ShoppingCartFragment;
import com.hamels.huanan.MemberCenter.Contract.FaqContract;
import com.hamels.huanan.MemberCenter.Presenter.FaqPresenter;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Faq;
import com.hamels.huanan.Utils.ArticleWebViewClient;
import com.hamels.huanan.Utils.PicassoImageGetter;

import org.json.JSONObject;

import java.util.Objects;

public class TermsOfUseActivity extends BaseActivity implements FaqContract.View{
    public static final String TAG = MainActivity.class.getSimpleName();

//    private FaqContract.Presenter presenter;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

//        presenter = new FaqPresenter(this, getRepositoryManager(this));
//        presenter.getFaqData("1");

        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.privacy_policy);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        webView = findViewById(R.id.web_view);
        bindWebView(webView);
        webView.loadUrl(EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_CONTENT_URL + "?mode=Faq&id=1");
    }

    @Override
    public void setFaqData(Faq faq) {

    }
    @SuppressLint("SetJavaScriptEnabled")
    public void bindWebView(WebView webView) {
        this.webView = webView;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(getBaseContext())
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                result.cancel();
                return true;
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(title.indexOf("html") == -1 && title.indexOf("com") == -1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setAppTitleString(title);
                        }
                    }, 1500);
                }
            }
        });
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 禁用滾動條
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // WebView启用混合内容
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setUseWideViewPort(true);
        webView.addJavascriptInterface(new AndroidJsInterface(), "hamels");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                setBackButtonVisibility(view.canGoBack());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                try {
                    if (url.startsWith("https://maps.app.goo.gl/") || url.startsWith("https://www.google.com.tw/")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } else if (url.startsWith("http:") || url.startsWith("https:")) {
                        return false;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    return false;
                }
//            webView.loadUrl(url);
                return true;
            }
        });
    }
    public class AndroidJsInterface {

        @JavascriptInterface
        public String jsCall_getVariable(String Info) {
            String sData = "";
            try {
                switch (Info){
                    case "customer_id":
                        sData = EOrderApplication.CUSTOMER_ID;
                        break;
                    case "connection_name":
                        sData = EOrderApplication.dbConnectName;
                        break;
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return sData;
        }
    }
}
package com.hamels.huanan.Product.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Product;
import com.hamels.huanan.Utils.ArticleWebViewClient;

public class ProductDetailDescFragment extends BaseFragment {
    public static final String TAG = ProductDetailDescFragment.class.getSimpleName();

    private static ProductDetailDescFragment fragment;
    private Product product;
    private WebView webView;
    private TextView tvContent;

    public static ProductDetailDescFragment getInstance(Product product) {
        if (fragment == null) {
            fragment = new ProductDetailDescFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Product.TAG, product);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cketitor, container, false);
        if (getArguments() != null && getArguments().containsKey(Product.TAG)) {
            product = getArguments().getParcelable(Product.TAG);
        }
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (product != null) {
//            PicassoImageGetter imageGetter = new PicassoImageGetter(this.getContext(), tvContent);
//            Spannable html;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                html = (Spannable) Html.fromHtml(product.getDesc(), Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//            } else {
//                html = (Spannable) Html.fromHtml(product.getDesc(), imageGetter, null);
//            }
//
//            tvContent.setText(html);

            webView.getSettings().setJavaScriptEnabled(true);   //支持javascript
            webView.setWebViewClient(new ArticleWebViewClient());
            webView.loadData(product.getDesc(), "text/html", "UTF-8");
        }
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).setAppTitle(R.string.product_desc);
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

        //tvContent = view.findViewById(R.id.tv_product_content);
        webView = view.findViewById(R.id.web_view);
    }
}

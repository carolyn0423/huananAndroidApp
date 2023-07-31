package com.hamels.huanan.Login.VIew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseActivity;
import com.hamels.huanan.Main.Contract.ScannerContract;
import com.hamels.huanan.R;

public class ScannerActivity extends BaseActivity implements ScannerContract.View {
    public static final String TAG = ScannerActivity.class.getSimpleName();

    private static ScannerActivity fragment;
    private RecyclerView recyclerView;

    //private BarcodeDetector barcodeDetector;

    private SurfaceView surfaceView;
    private TextView tvCustomerNo, tvSend;

    @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initView();
    }

    private void initView() {
        setAppToolbar(R.id.toolbar);
        setAppTitle(R.string.tab_scanner);
        setBackButtonVisibility(true);
        setMailButtonVisibility(false);
        setMessageButtonVisibility(false);
        setSortButtonVisibility(false);
        setAppToolbarVisibility(true);

        surfaceView = findViewById(R.id.surfaceView);
        tvCustomerNo = findViewById(R.id.tv_customer_no);
        tvSend = findViewById(R.id.tv_scan);

    }
}

package com.hamels.huanan.Donate.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Donate.Presenter.DonateDetailPresenter;
import com.hamels.huanan.Login.VIew.LoginActivity;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.Donate.Contract.DonateDetailContract;
//import com.hamels.huanan.Donate.Presenter.DonateDetailPresenter;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.Donate;
//import com.hamels.huanan.Repository.Model.DonatePicture;
//import com.hamels.huanan.Repository.Model.DonateSpec;
import com.hamels.huanan.EOrderApplication;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class DonateDetailFragment extends BaseFragment implements DonateDetailContract.View {
    public static final String TAG = DonateDetailFragment.class.getSimpleName();
    private static final String UID = "uid";
    private static DonateDetailFragment fragment;
    private DonateDetailContract.Presenter presenter;

    public ImageView img_donate, img_barcode;
    private TextView tv_barcode_number, tv_product_name, tv_type_name_spec_name, tv_RowNo, tv_TotalNumber, tv_eticket_due_date;
    private ImageButton btn_close;
    private Button btn_donatedetail2;
    private ConstraintLayout layout_left_arrow, layout_right_arrow;

    private int uid = 0;
    private String ticket_code = "";
    private int brightnessNow = 0;

    public static DonateDetailFragment getInstance(int uid) {
        if (fragment == null) {
            fragment = new DonateDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(UID, uid);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_detail, container, false);
        if (getArguments() != null) {
            uid = getArguments().getInt(UID, 0);
            Log.e(TAG, uid + "");
            initView(view);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(false);

        ((MainActivity) getActivity()).setAppTitle(R.string.tab_donate);

        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(false);
        ((MainActivity) getActivity()).setMailButtonVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        // 亮度
        brightnessNow = getSystemBrightness();
        changeAppBrightness(255);

        img_donate = view.findViewById(R.id.img_donate);
        img_barcode = view.findViewById(R.id.img_barcode);
        tv_barcode_number = view.findViewById(R.id.tv_barcode_number);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_type_name_spec_name = view.findViewById(R.id.tv_type_name_spec_name);
        //tv_spec_name = view.findViewById(R.id.tv_spec_name);
        tv_RowNo = view.findViewById(R.id.tv_RowNo);
        tv_TotalNumber = view.findViewById(R.id.tv_TotalNumber);
        btn_close = view.findViewById(R.id.btn_close);
        btn_donatedetail2 = view.findViewById(R.id.btn_donatedetail2);
        layout_left_arrow = view.findViewById(R.id.layout_left_arrow);
        layout_right_arrow = view.findViewById(R.id.layout_right_arrow);
        tv_eticket_due_date = view.findViewById(R.id.tv_eticket_due_date);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_donatedetail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();

                ((MainActivity) getActivity()).addFragment(DonateDetail2Fragment.getInstance(uid));
            }
        });

        layout_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNewUID = v.getTag(v.getId()).toString();
                if (!sNewUID.isEmpty()) {
                    int iNewUID = Integer.parseInt(sNewUID);
                    getInstance(iNewUID);
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.detach(fragment);
                    ft.attach(fragment);
                    ft.commit();
                }
            }
        });

        layout_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNewUID = v.getTag(v.getId()).toString();
                if (!sNewUID.isEmpty()) {
                    int iNewUID = Integer.parseInt(sNewUID);
                    getInstance(iNewUID);
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.detach(fragment);
                    ft.attach(fragment);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        changeAppBrightness(brightnessNow);
    }

    @Override
    public void setDonateDetail(List<Donate> productDetail) {
        String sPictureUrl = productDetail.get(0).getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productDetail.get(0).getPictureUrl();
        Glide.with(DonateDetailFragment.getInstance(uid)).load(EOrderApplication.sApiUrl + sPictureUrl).into(img_donate);

        ticket_code = productDetail.get(0).getTicketCode();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(ticket_code, BarcodeFormat.CODE_39, 400, 100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img_barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        if (productDetail.get(0).getTicketStatus().equals("R")) {
            btn_donatedetail2.setVisibility(View.GONE);
        }

        tv_barcode_number.setText(ticket_code);
        tv_product_name.setText(productDetail.get(0).getProductName());
        tv_type_name_spec_name.setText(" ( " + productDetail.get(0).getTypeName() + " - " + productDetail.get(0).getSpecName() + " ) ");
        //tv_spec_name.setText(productDetail.get(0).getSpecName());
        tv_RowNo.setText(productDetail.get(0).getRowNo());
        tv_TotalNumber.setText(productDetail.get(0).getTotalNumber());
        layout_left_arrow.setTag(R.id.layout_left_arrow, productDetail.get(0).getLastUid());
        layout_right_arrow.setTag(R.id.layout_right_arrow, productDetail.get(0).getNextUid());
        tv_eticket_due_date.setText(productDetail.get(0).getEticketDueDate());
    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        ((MainActivity) getActivity()).refreshBadge();
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new DonateDetailPresenter(this, getRepositoryManager(getContext()));
        presenter.getDonateDetailByID(Integer.toString(uid));
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    public void changeAppBrightness(int brightness) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }
}


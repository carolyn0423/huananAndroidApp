package com.hamels.huanan.Main.View;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Main.Contract.MemberCardContract;
import com.hamels.huanan.Main.Presenter.MemberCardPresenter;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.User;

public class MemberCardFragment extends BaseFragment implements MemberCardContract.View {
    public static final String TAG = MemberCardFragment.class.getSimpleName();

    private static MemberCardFragment fragment;
    private ConstraintLayout memberCardFront, memberCardBack;
    private ImageView imgBarcode, imgBgCardBack, imgBgCardFront;
    private ImageButton btnToBack, btnToFront;
    private TextView tvBarcodeNumber, tvLevel, tvName, tvBirth, tvPoint, tvCellphone,tvPrepaidAmount;

    private AnimatorSet rightOutSet, leftInSet;
    private MemberCardContract.Presenter memberCardPresenter;

    public static MemberCardFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberCardFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_card, container, false);
        initView(view);
//        initAnimation();
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_member_card);
        ((MainActivity) getActivity()).refreshBadge();
        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        memberCardFront = view.findViewById(R.id.layout_card_front);
        memberCardBack = view.findViewById(R.id.layout_card_back);

        imgBarcode = view.findViewById(R.id.img_barcode);
        imgBgCardBack = view.findViewById(R.id.bg_member_card_back);
        imgBgCardFront = view.findViewById(R.id.bg_member_card_front);

//        btnToBack = view.findViewById(R.id.btn_to_back);
//        btnToBack.setOnClickListener(this);
//
//        btnToFront = view.findViewById(R.id.btn_to_front);
//        btnToFront.setOnClickListener(this);

        tvBarcodeNumber = view.findViewById(R.id.tv_barcode_number);
//        tvLevel = view.findViewById(R.id.tv_member_type);
        //暫時隱藏
//        tvPrepaidAmount = view.findViewById(R.id.tv_prepaid_amount);
        tvName = view.findViewById(R.id.tv_name);
        tvBirth = view.findViewById(R.id.tv_birth);
        tvPoint = view.findViewById(R.id.btn_point);
        tvCellphone = view.findViewById(R.id.tv_cellphone);

        memberCardPresenter = new MemberCardPresenter(this, getRepositoryManager(getContext()));
        memberCardPresenter.getMemberInfo();
    }

    private void initAnimation() {
        setAnimators();
        setCameraDistance();
    }

    private void setAnimators() {
        rightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_card_out);
        leftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_card_in);

        rightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                btnToBack.setClickable(false);
                btnToFront.setClickable(false);
            }
        });
        leftInSet.addListener( new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnToBack.setClickable(true);
                btnToFront.setClickable(true);
            }
        });
    }

    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        memberCardFront.setCameraDistance(scale);
        memberCardBack.setCameraDistance(scale);
    }

    private void flipCard(boolean isToBack) {
        rightOutSet.setTarget(isToBack ? memberCardFront : memberCardBack);
        leftInSet.setTarget(isToBack ? memberCardBack : memberCardFront);
        rightOutSet.start();
        leftInSet.start();

        // imgBarcode.setOnClickListener(isToBack ? this : null);
    }

    @Override
    public void setUerInfo(User user) {
//        tvLevel.setText(user.isVipUser() ? R.string.type_vip : R.string.type_normal);
//        imgBgCardBack.setImageResource(user.isVipUser() ? R.drawable.bg_vip : R.drawable.bg_normal);
//        imgBgCardFront.setImageResource(user.isVipUser() ? R.drawable.bg_vip : R.drawable.bg_normal);

        if(user.getGroup()!=null){
            if(user.getGroup().equals("V")){
                imgBgCardBack.setImageResource(R.drawable.bg_vip);
            }else{
                imgBgCardBack.setImageResource(R.drawable.bg_normal);
            }
        }else{
            imgBgCardBack.setImageResource(R.drawable.bg_normal);
        }

        tvName.setText(user.getName());

        if(user.getBirthday()!=null){
            if(user.getBirthday().equals("")){
                tvBirth.setText("尚未填寫");
                tvBirth.setTextColor(Color.parseColor("#c7c7c7"));
            }else{
                tvBirth.setText(user.getBirthday());
                tvBirth.setTextColor(Color.parseColor("#333333"));
            }
        }

        tvPoint.setText(user.getPoint());
        tvCellphone.setText(user.getMobile());

        //暫時隱藏
//        tvPrepaidAmount.setText(user.getprePaidAmount());
    }

    @Override
    public void setMemberCardInfo(String cardNum) {
        if(cardNum!=null){
            if(cardNum.equals("")){
                Glide
                        .with(getActivity())
                        .load(R.drawable.barcod)
                        .into(imgBarcode);
                tvBarcodeNumber.setText("無會員卡資訊");
            }
            else {
                imgBarcode.setBackgroundColor(Color.WHITE);
                tvBarcodeNumber.setText(cardNum);
                if (isAdded()) {
//            Glide.with(this).load(barcodeUrl).into(imgBarcode);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(cardNum, BarcodeFormat.CODE_39, 400, 56);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imgBarcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

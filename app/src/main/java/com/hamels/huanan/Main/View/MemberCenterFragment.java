package com.hamels.huanan.Main.View;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Main.Contract.MemberCenterContract;
import com.hamels.huanan.Main.Presenter.MemberCenterPresenter;
import com.hamels.huanan.MemberCenter.View.AboutFragment;
import com.hamels.huanan.MemberCenter.View.FaqFragment;
import com.hamels.huanan.MemberCenter.View.MemberInfoChangeFragment;
import com.hamels.huanan.MemberCenter.View.MemberPointFragment;
import com.hamels.huanan.MemberCenter.View.MessageListFragment;
import com.hamels.huanan.MemberCenter.View.PasswordChangeFragment;
import com.hamels.huanan.MemberCenter.View.WebViewFragment;
import com.hamels.huanan.R;
import com.hamels.huanan.EOrderApplication;
import com.hamels.huanan.Repository.Model.User;

import java.util.Objects;

public class MemberCenterFragment extends BaseFragment implements View.OnClickListener, MemberCenterContract.View {
    public static final String TAG = MemberCenterFragment.class.getSimpleName();

    private LinearLayout btnChangePassword, btnTransRecord, btnPoint, btnCoupon, btnContactUs, btnPrivacy, btnTerms, btnCustomerservice, btnMemberLogout;
    private TextView tvName, tvPoint, tvPhone;
    private ImageView btnMemberInfo;
    private PopupWindow popupWindow;
    private ConstraintLayout btnlogout, btndelete;

    private int barcodeWidth = 350;
    private int barcodeHeight = 350;
    private int brightnessNow = 0;

    private static MemberCenterFragment fragment;
    private MemberCenterContract.Presenter memberPresenter;

    public static MemberCenterFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberCenterFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_center, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberPresenter = new MemberCenterPresenter(this, getRepositoryManager(getContext()));
        memberPresenter.getUserInfo();
        memberPresenter.getPointData();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_member_info);

        ((MainActivity) getActivity()).refreshBadge();

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        memberPresenter = new MemberCenterPresenter(this, getRepositoryManager(getContext()));

        btnlogout = view.findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(this);

        btndelete = view.findViewById(R.id.btn_delete);
        btndelete.setOnClickListener(this);

//        img_qrcode = view.findViewById(R.id.img_qrcode);
//        img_qrcode.setOnClickListener(this);
        tvName = view.findViewById(R.id.tv_name);
        tvPoint = view.findViewById(R.id.tv_point);
        tvPhone = view.findViewById(R.id.tv_phone);

        btnMemberInfo = view.findViewById(R.id.btn_member_info);
        btnMemberInfo.setOnClickListener(this);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnChangePassword.setOnClickListener(this);

        btnTransRecord = view.findViewById(R.id.btn_trans_record);
        btnTransRecord.setOnClickListener(this);
        btnPoint = view.findViewById(R.id.btn_point);
        btnPoint.setOnClickListener(this);
        btnCoupon = view.findViewById(R.id.btn_coupon);
        btnCoupon.setOnClickListener(this);

        btnContactUs = view.findViewById(R.id.btn_contact_us);
        btnContactUs.setOnClickListener(this);
        btnPrivacy = view.findViewById(R.id.btn_privacy);
        btnPrivacy.setOnClickListener(this);
        btnTerms = view.findViewById(R.id.btn_terms);
        btnTerms.setOnClickListener(this);

        btnCustomerservice = view.findViewById(R.id.btn_customerservice);
        btnCustomerservice.setOnClickListener(this);

        btnMemberLogout = view.findViewById(R.id.btn_member_logout);
        btnMemberLogout.setOnClickListener(this);

        if(!memberPresenter.getUserLogin()){
            memberPresenter.saveSourceActive("");
            ((MainActivity) getActivity()).changeTabFragment(MainIndexFragment.getInstance());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_member_info){
            ((MainActivity) getActivity()).addFragment(MemberInfoChangeFragment.getInstance());
        }else if (id == R.id.btn_change_password){
            ((MainActivity) getActivity()).addFragment(PasswordChangeFragment.getInstance());
        }else if (id == R.id.btn_point){
            ((MainActivity) getActivity()).addFragment(MemberPointFragment.getInstance());
        }else if (id == R.id.btn_coupon){
            ((MainActivity) getActivity()).addFragment(WebViewFragment.getInstance(R.string.coupon, EOrderApplication.sApiUrl + EOrderApplication.WEBVIEW_COUPONS_URL));
        }else if (id == R.id.btn_contact_us){
            ((MainActivity) getActivity()).addFragment(AboutFragment.getInstance());
        }else if (id == R.id.btn_terms){
            ((MainActivity) getActivity()).addFragment(FaqFragment.getInstance("1"));
        }else if (id == R.id.btn_privacy){
            ((MainActivity) getActivity()).addFragment(FaqFragment.getInstance("2"));
        }else if (id == R.id.btn_trans_record){
            ((MainActivity) getActivity()).addFragment(TransRecordFragment.getInstance("G", "", ""));
        }else if (id == R.id.btn_logout){

            memberPresenter.logout();
        }else if (id == R.id.btn_customerservice){
            EOrderApplication.MESSAGE_TAG = "";
            ((MainActivity) getActivity()).addFragment(MessageListFragment.getInstance());
//                Toast.makeText(getActivity(), "此功能未開放", Toast.LENGTH_LONG).show();
        }else if(id == R.id.btn_member_logout || id == R.id.btn_delete){
            //  刪除會員
            new AlertDialog.Builder(getContext()).setTitle("確定刪除帳號?").setMessage(R.string.delete_member)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            memberPresenter.getDeleteMember();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }

    @Override
    public void setUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void setUserPoint(String point) {
        tvPoint.setText(point);
    }

    @Override
    public void setUserMobile(String mobile) {
        tvPhone.setText(mobile);
    }

    @Override
    public void redirectToMainPage() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setTabPage(0);
        ((MainActivity) Objects.requireNonNull(getActivity())).refreshBadge();
    }

    @Override
    public void setPointData(User user) {
        tvPoint.setText(user.getPoint());
        //tvExpireTime.setText(String.format(getString(R.string.expire_date), Calendar.getInstance().get(Calendar.YEAR) + "/12/31",user.getBonusexpiredsoon()));
    }

    public void deleteMember(){
        new AlertDialog.Builder(getContext()).setTitle(null).setMessage(R.string.logout_success)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        memberPresenter.logout();
                    }
                })
                .show();
    }
    public void deleteError(String sMessage){
        new AlertDialog.Builder(getContext()).setTitle(null).setMessage(sMessage)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
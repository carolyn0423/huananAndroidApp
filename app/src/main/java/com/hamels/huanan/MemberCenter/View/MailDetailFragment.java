package com.hamels.huanan.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.MemberMessage;

public class MailDetailFragment extends BaseFragment {
    public static final String TAG = MailDetailFragment.class.getSimpleName();

    private static MailDetailFragment fragment;
    private MemberMessage memberMessage;

    private TextView tvMailTitle, tvMailContent;

    public static MailDetailFragment getInstance(MemberMessage memberMessage) {
        if (fragment == null) {
            fragment = new MailDetailFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(MemberMessage.TAG, memberMessage);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail_detail, container, false);
        if (getArguments() != null && getArguments().containsKey(MemberMessage.TAG)) {
            memberMessage = getArguments().getParcelable(MemberMessage.TAG);
        }
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (memberMessage != null) {
            tvMailTitle.setText(memberMessage.getTitle());
            tvMailContent.setText(Html.fromHtml(memberMessage.getContent()));
        }
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).EditFragmentBottom(true, true);
        ((MainActivity) getActivity()).setAppTitle(R.string.mail_file);
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

        tvMailTitle = view.findViewById(R.id.tv_mail_title);
        tvMailContent = view.findViewById(R.id.tv_mail_content);
    }
}

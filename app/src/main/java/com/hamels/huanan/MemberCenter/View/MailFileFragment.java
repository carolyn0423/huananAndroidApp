package com.hamels.huanan.MemberCenter.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.huanan.Base.BaseFragment;
import com.hamels.huanan.Main.View.MainActivity;
import com.hamels.huanan.MemberCenter.Adapter.MailFileAdapter;
import com.hamels.huanan.MemberCenter.MailFileContract;
import com.hamels.huanan.MemberCenter.Presenter.MailFilePresenter;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.MemberMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailFileFragment extends BaseFragment implements MailFileContract.View {
    public static final String TAG = MailFileFragment.class.getSimpleName();

    private static MailFileFragment fragment;

    private RecyclerView recyclerView;
    private MailFileAdapter mailFileAdapter;

    private MailFileContract.Presenter mailPresenter;

    public static MailFileFragment getInstance() {
        if (fragment == null) {
            fragment = new MailFileFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail_file, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mailPresenter.getMessageList();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.mail_file);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);

        mailPresenter = new MailFilePresenter(this, getRepositoryManager(getContext()));

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setHasFixedSize(true);

        mailFileAdapter = new MailFileAdapter(mailPresenter);
        recyclerView.setAdapter(mailFileAdapter);
    }

    @Override
    public void showMailDetail(MemberMessage memberMessage) {
        ((MainActivity) getActivity()).addFragment(MailDetailFragment.getInstance(memberMessage));
    }

    @Override
    public void setMessageList(List<MemberMessage> memberMessages) {
        Map<Integer, Boolean> isReadMap = new HashMap<>();
        for(MemberMessage memberMessage : memberMessages){
            Log.e(TAG,memberMessage.getTitle());
            Log.e(TAG,memberMessage.getContent());
            Log.e(TAG,memberMessage.getCompletedAt());
            if(memberMessage.getRead_flag().equals("Y")){
                isReadMap.put(memberMessage.getId(),true);
            }
        }
        mailFileAdapter.setMemberMessages(memberMessages, isReadMap);
    }
}

package com.hamels.huanan.MemberCenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hamels.huanan.Base.BaseAdapter;
import com.hamels.huanan.MemberCenter.Contract.AdminMessageContract;
import com.hamels.huanan.MemberCenter.Holder.AdminMessageHolder;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.MessageGroup;

import java.util.ArrayList;
import java.util.List;

public class AdminMessageAdapter extends BaseAdapter<AdminMessageHolder> {
    public static final String TAG = AdminMessageAdapter.class.getSimpleName();
    private List<MessageGroup> messageGroups = new ArrayList<>();
    private AdminMessageContract.Presenter presenter;

    public AdminMessageAdapter(AdminMessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public AdminMessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_group, viewGroup, false);
        return new AdminMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMessageHolder mHolder, final int position) {
        MessageGroup messageGroup = messageGroups.get(position);

        mHolder.setMessage(messageGroup);

        mHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goMessageList(messageGroups.get(position));
                //presenter.readMessage(memberMessages.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageGroups.size();
    }

    public void setMessagesList(List<MessageGroup> Messages) {
        this.messageGroups = Messages;

        notifyDataSetChanged();
    }
}

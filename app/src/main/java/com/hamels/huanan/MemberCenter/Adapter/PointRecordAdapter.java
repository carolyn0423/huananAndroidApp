package com.hamels.huanan.MemberCenter.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.huanan.Base.BaseAdapter;
import com.hamels.huanan.MemberCenter.MemberPointContract;
import com.hamels.huanan.MemberCenter.Holder.PointRecordHolder;
import com.hamels.huanan.R;
import com.hamels.huanan.Repository.Model.PointHistory;

import java.util.ArrayList;
import java.util.List;

public class PointRecordAdapter extends BaseAdapter<PointRecordHolder> {
    public static final String TAG = PointRecordAdapter.class.getSimpleName();
    private MemberPointContract.Presenter presenter;

    private List<PointHistory> pointHistoryList = new ArrayList<>();

    public PointRecordAdapter(MemberPointContract.Presenter presenter){
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public PointRecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_point_record, viewGroup, false);
        return new PointRecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PointRecordHolder pointRecordHolder, int position) {
        Log.e(TAG,pointHistoryList.toString());
        pointRecordHolder.setPointLog(pointHistoryList.get(position));
    }

    public void setList(List<PointHistory> pointHistoryList) {
        Log.e(TAG,"setList : " + pointHistoryList.toString());
        this.pointHistoryList = pointHistoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pointHistoryList.size();
    }
}

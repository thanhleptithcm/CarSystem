package com.matas.caroperatingsystem.ui.fragment.manage_staff_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matas.caroperatingsystem.R;

import java.util.List;

public class DetailManageStaffAdapter extends RecyclerView.Adapter<DetailManageStaffAdapter.StaffHolder> {

    private Context mContext;
    private List<String> mList;
    private OnItemClickListener mListener;

    public DetailManageStaffAdapter(Context context, List<String> list, OnItemClickListener callBack) {
        this.mContext = context;
        this.mList = list;
        this.mListener = callBack;
    }

    @Override
    public StaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_manage_detail_staff, parent, false);
        return new StaffHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class StaffHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        StaffHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

package com.matas.caroperatingsystem.ui.fragment.staff;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.widget.AppTextView;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffHolder> {

    private Context mContext;
    private List<String> mList;
    private OnItemClickListener mListener;

    public StaffAdapter(Context context, List<String> list, OnItemClickListener callBack) {
        this.mContext = context;
        this.mList = list;
        this.mListener = callBack;
    }

    @Override
    public StaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_staff, parent, false);
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
        private AppTextView mNameTextView;
        private AppTextView mPhoneNumberTextView;

        StaffHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.tv_name);
            mPhoneNumberTextView = itemView.findViewById(R.id.tv_phone);

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

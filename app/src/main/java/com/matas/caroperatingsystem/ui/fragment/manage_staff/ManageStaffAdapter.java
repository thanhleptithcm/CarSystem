package com.matas.caroperatingsystem.ui.fragment.manage_staff;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppTextView;

import java.util.List;

public class ManageStaffAdapter extends RecyclerView.Adapter<ManageStaffAdapter.StaffHolder> {

    private Context mContext;
    private List<User> mList;
    private OnItemClickListener mListener;

    public ManageStaffAdapter(Context context, List<User> list, OnItemClickListener callBack) {
        this.mContext = context;
        this.mList = list;
        this.mListener = callBack;
    }

    @Override
    public StaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_manage_staff, parent, false);
        return new StaffHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffHolder holder, int position) {
        User user = mList.get(position);

        holder.mNameTextView.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        holder.mPhoneNumberTextView.setText(user.getPhone());
        holder.mGenderImageView.setImageResource(user.getGender().equalsIgnoreCase("male") ? R.drawable.ic_male : R.drawable.ic_female);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class StaffHolder extends RecyclerView.ViewHolder {
        private AppTextView mNameTextView;
        private AppTextView mPhoneNumberTextView;
        private ImageView mGenderImageView;
        private AppButton mStatusButton;

        StaffHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.tv_name);
            mPhoneNumberTextView = itemView.findViewById(R.id.tv_phone);
            mGenderImageView = itemView.findViewById(R.id.imv_gender);
            mStatusButton = itemView.findViewById(R.id.btn_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

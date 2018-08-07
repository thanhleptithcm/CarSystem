package com.matas.caroperatingsystem.ui.fragment.manage_staff_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.utils.CommonUtils;
import com.matas.caroperatingsystem.widget.AppTextView;

import java.util.List;

public class DetailManageStaffAdapter extends RecyclerView.Adapter<DetailManageStaffAdapter.StaffHolder> {

    private Context mContext;
    private List<Book> mList;
    private OnItemClickListener mListener;

    public DetailManageStaffAdapter(Context context, List<Book> list, OnItemClickListener callBack) {
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
        Book book = mList.get(position);
        holder.tvPhone.setText(book.getPassenger().getPhone());
        holder.tvPrice.setText(String.format("%s %s", String.valueOf(book.getDistance() * CommonUtils.getPrice()), " VND"));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class StaffHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppTextView tvPhone;
        AppTextView tvPrice;

        StaffHolder(View itemView) {
            super(itemView);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvPrice = itemView.findViewById(R.id.tv_price);
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

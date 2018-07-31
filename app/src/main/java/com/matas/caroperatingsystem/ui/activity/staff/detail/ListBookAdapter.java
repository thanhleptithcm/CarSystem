package com.matas.caroperatingsystem.ui.activity.staff.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppTextView;

import java.util.List;

public class ListBookAdapter extends RecyclerView.Adapter<ListBookAdapter.BookHolder> {

    private Context mContext;
    private List<Book> mList;
    private OnItemClickListener mListener;

    public ListBookAdapter(Context context, List<Book> list, OnItemClickListener callBack) {
        this.mContext = context;
        this.mList = list;
        this.mListener = callBack;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_booking, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        Book book = mList.get(position);

        holder.mPhoneTextView.setText(book.getPassenger().getPhone());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private AppTextView mPhoneTextView;
        private AppButton mAcceptTextView;

        BookHolder(View itemView) {
            super(itemView);
            mPhoneTextView = itemView.findViewById(R.id.tv_phone);
            mAcceptTextView = itemView.findViewById(R.id.btn_accept);

            mAcceptTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onAccept(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onAccept(int position);
    }
}

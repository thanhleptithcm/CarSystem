package com.matas.caroperatingsystem.ui.activity.staff.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matas.caroperatingsystem.R;
import com.matas.caroperatingsystem.data.model.Book;
import com.matas.caroperatingsystem.utils.CommonUtils;
import com.matas.caroperatingsystem.widget.AppButton;
import com.matas.caroperatingsystem.widget.AppTextView;

import java.util.List;

public class ListBookAdapter extends RecyclerView.Adapter<ListBookAdapter.BookHolder> {

    private Context mContext;
    private List<Book> mList;

    public ListBookAdapter(Context context, List<Book> list) {
        this.mContext = context;
        this.mList = list;
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
        holder.mPriceTextView.setText(String.format("%s %s", String.valueOf(book.getDistance() * CommonUtils.getPrice()), " VND"));

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private AppTextView mPhoneTextView;
        private AppTextView mPriceTextView;

        BookHolder(View itemView) {
            super(itemView);
            mPhoneTextView = itemView.findViewById(R.id.tv_phone);
            mPriceTextView = itemView.findViewById(R.id.tv_price);
        }
    }
}

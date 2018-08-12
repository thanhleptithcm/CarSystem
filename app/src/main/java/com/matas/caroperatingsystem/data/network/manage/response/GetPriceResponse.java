package com.matas.caroperatingsystem.data.network.manage.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Price;
import com.matas.caroperatingsystem.data.network.BaseResponse;

import java.util.List;

public class GetPriceResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private DataPrice dataPrice;

    public DataPrice getDataPrice() {
        return dataPrice;
    }

    public void setDataPrice(DataPrice dataPrice) {
        this.dataPrice = dataPrice;
    }

    public class DataPrice implements Parcelable {
        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("byTime")
        @Expose
        private List<Price> priceByTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Price> getPriceByTime() {
            return priceByTime;
        }

        public void setPriceByTime(List<Price> priceByTime) {
            this.priceByTime = priceByTime;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeTypedList(this.priceByTime);
        }

        public DataPrice() {
        }

        protected DataPrice(Parcel in) {
            this.id = in.readString();
            this.priceByTime = in.createTypedArrayList(Price.CREATOR);
        }

        public final Creator<DataPrice> CREATOR = new Creator<DataPrice>() {
            @Override
            public DataPrice createFromParcel(Parcel source) {
                return new DataPrice(source);
            }

            @Override
            public DataPrice[] newArray(int size) {
                return new DataPrice[size];
            }
        };
    }
}
